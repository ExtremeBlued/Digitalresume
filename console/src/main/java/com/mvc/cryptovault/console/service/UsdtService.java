
package com.mvc.cryptovault.console.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.mvc.cryptovault.common.bean.*;
import com.mvc.cryptovault.common.constant.RedisConstant;
import com.mvc.cryptovault.common.util.BaseContextHandler;
import com.mvc.cryptovault.common.util.ConditionUtil;
import com.mvc.cryptovault.console.bean.Balance;
import com.mvc.cryptovault.console.bean.UsdtTransaction;
import com.mvc.cryptovault.console.constant.BusinessConstant;
import com.mvc.cryptovault.console.util.btc.BtcAction;
import com.mvc.cryptovault.console.util.btc.entity.TetherBalance;
import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.CommunicationException;
import com.neemre.btcdcli4j.core.client.BtcdClient;
import com.neemre.btcdcli4j.core.domain.Block;
import com.neemre.btcdcli4j.core.domain.Output;
import com.neemre.btcdcli4j.core.domain.OutputOverview;
import com.neemre.btcdcli4j.core.domain.SignatureResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author qiyichen
 * @create 2018/11/29 14:03
 */
@Service("UsdtService")
@Transactional(rollbackFor = RuntimeException.class)
public class UsdtService extends BlockService {

    @Autowired
    BtcdClient btcdClient;
    @Autowired
    BlockSignService blockSignService;
    @Value("${usdt.propId}")
    private Integer propId;
    private static String nowHash = "";
    private AdminWallet hotWallet = null;
    @Autowired
    CommonTokenService commonTokenService;
    @Autowired
    BlockUsdtWithdrawQueueService blockUsdtWithdrawQueueService;
    @Autowired
    BlockHotAddressService blockHotAddressService;
    //发送usdt时必然会带上这笔金额,因此发送手续费时需要额外发送这笔数量,否则会从预设手续费中扣除从而导致和期望结果不一致
    private final BigDecimal USDT_LIMIT_FEE = new BigDecimal("0.00000546");
    //地址处于等待中时隔一段时间后运行
    private Long APPROVE_WAIT = 1000 * 60L;

    @Override
    public BigInteger getNonce(Map<String, BigInteger> nonceMap, String address) throws IOException {
        return null;
    }

    @Override
    public BigInteger getEthEstimateTransferFrom(String contractAddress, String from, String to) throws IOException {
        return null;
    }

    @Override
    public BigInteger getEthEstimateApprove(String contractAddress, String from, String to) throws IOException {
        return null;
    }

    @Override
    public void send(AdminWallet hot, String address, BigDecimal fromWei) throws IOException {

    }

    @Override
    public BigDecimal getBalance(String tokenName) {
        return null;
    }

    @Override
    public BigInteger getEthEstimateTransfer(String tokenContractAddress, String toAddress, String address, BigDecimal value) throws IOException {
        return null;
    }

    @Override
    public void run(String... args) throws Exception {
        executorService.execute(() -> {
            BaseContextHandler.set("thread-key", "btcOldListener");
            try {
                oldListener();
            } catch (Exception e) {
                oldListener();
            }
        });
        executorService.execute(() -> {
            BaseContextHandler.set("thread-key", "btcSignJob");
            try {
                signJob();
            } catch (Exception e) {
                signJob();
            }
        });
        executorService.execute(() -> {
            BaseContextHandler.set("thread-key", "btcQueueJob");
            try {
                queueJob();
            } catch (Exception e) {
                queueJob();
            }
        });
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void queueJob() {
        while (true) {
            List<BlockUsdtWithdrawQueue> list = blockUsdtWithdrawQueueService.findStart();
            for (BlockUsdtWithdrawQueue obj : list) {
                try {
                    String result = BtcAction.prepareCollection(obj.getFromAddress(), obj.getToAddress(), obj.getFee(), obj.getValue());
                    SignatureResult raw = btcdClient.signRawTransaction(result);
                    if (!raw.getComplete()) {
                        obj.setStatus(9);
                        blockUsdtWithdrawQueueService.update(obj);
                        continue;
                    }
                    String hash = btcdClient.sendRawTransaction(raw.getHex());
                    obj.setStatus(2);
                    blockUsdtWithdrawQueueService.update(obj);
                    blockTransactionService.updateHash(obj.getOrderId(), hash);
                } catch (Exception e) {
                    if (e.getMessage().equalsIgnoreCase("Error #-26: 258: txn-mempool-conflict") || e.getMessage().startsWith("No unspent on address")) {
                        //该种错误添加到重试列表
                        obj.setStartedAt(System.currentTimeMillis() + APPROVE_WAIT);
                        blockUsdtWithdrawQueueService.update(obj);
                        continue;
                    }
                    obj.setStatus(9);
                    blockUsdtWithdrawQueueService.update(obj);
                    continue;
                }
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    private void signJob() {

        while (true) {
            try {
                BlockSign sign = blockSignService.findOneByToken("BTC");
                sign(sign);
                Thread.sleep(20);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sign(BlockSign sign) {
        try {
            if (null == sign) {
                return;
            }
            String result = btcdClient.sendRawTransaction(sign.getSign());
            sign.setHash(result);
            sign.setStatus(1);
            blockSignService.update(sign);
            blockTransactionService.updateHash(sign.getOrderId(), result);
            //开始更新usdt排队列表
            blockUsdtWithdrawQueueService.start(sign.getOrderId(), sign.getToAddress());
        } catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase("Error #-26: 258: txn-mempool-conflict")) {
                //该种错误添加到重试列表
                sign.setStartedAt(System.currentTimeMillis() + APPROVE_WAIT);
                blockSignService.update(sign);
                return;
            }
            sign.setStatus(9);
            sign.setResult(e.getMessage());
            blockSignService.update(sign);
            if (StringUtils.isNotBlank(sign.getOrderId())) {
                //更新区块交易表
                String message = "交易失败";
                String data = e.getMessage();
                updateError(sign.getOrderId(), message, data);
            }
        }
    }

    private BlockTransaction blockTransaction(UsdtTransaction tx) throws IOException {
        BlockTransaction transaction = new BlockTransaction();
        BigDecimal value = new BigDecimal(tx.getAmount());
        CommonAddress address = isOurAddress(tx.getSendingaddress(), tx.getReferenceaddress());
        //非内部地址忽略
        if (null == address) {
            return null;
        }
        Long time = System.currentTimeMillis();
        transaction.setFromAddress(tx.getSendingaddress());
        transaction.setToAddress(tx.getReferenceaddress());
        transaction.setHash(tx.getTxid());
        transaction.setUpdatedAt(time);
        transaction.setTokenType("BTC");
        transaction.setCreatedAt(time);
        transaction.setValue(value);
        transaction.setUserId(address.getUserId());
        transaction.setOrderNumber("");
        transaction.setFee(new BigDecimal(tx.getFee()));
        transaction.setHeight(tx.getBlock());
        transaction.setStatus(tx.getConfirmations().compareTo(BigInteger.valueOf(6)) >= 0 ? 2 : 1);
        transaction.setTransactionStatus(tx.getConfirmations().compareTo(BigInteger.valueOf(6)) >= 0 ? 5 : 4);
        //根据地址判断操作类型
        if (address.getUserId().equals(BigInteger.ZERO)) {
            transaction.setOprType(9);
        } else if (tx.getReferenceaddress().equalsIgnoreCase(address.getAddress())) {
            transaction.setOprType(1);
        } else {
            transaction.setOprType(2);
        }
        transaction.setTokenId(BusinessConstant.BASE_TOKEN_ID_USDT);
        if (tx.getValid() == false) {
            //校验是否成功
            transaction.setStatus(9);
            transaction.setTransactionStatus(6);
            transaction.setErrorMsg(tx.getInvalidreason());
            transaction.setErrorData(tx.getInvalidreason());
        }
        return transaction;
    }

    private void oldListener() {
        String lastNumber = getHeight();
        Block block = null;
        while (true) {
            try {
                Thread.sleep(100);
                String height = btcdClient.getBlockChainInfo().getBestBlockHash();
                block = btcdClient.getBlock(lastNumber);
                Boolean ignore = isIgnore(lastNumber, block, height);
                if (ignore) {
                    continue;
                }
                List<String> txList = btcdClient.getBlock(block.getHash()).getTx();
                readTxList(txList);
                nowHash = null == block ? nowHash : block.getHash();
                lastNumber = block.getNextBlockHash();
                redisTemplate.opsForValue().set(RedisConstant.USDT_LAST_HEIGHT, lastNumber);
                updateStatus(block.getHeight().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void readTxList(List<String> txList) {
        for (String txId : txList) {
            try {