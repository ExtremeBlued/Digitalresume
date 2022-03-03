package com.mvc.cryptovault.console.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mvc.cryptovault.common.bean.AdminWallet;
import com.mvc.cryptovault.common.bean.BlockTransaction;
import com.mvc.cryptovault.common.bean.CommonAddress;
import com.mvc.cryptovault.common.bean.CommonToken;
import com.mvc.cryptovault.console.config.SpringContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.*;

@Component
public abstract class BlockService implements CommandLineRunner {

    @Autowired
    protected BlockTransactionService blockTransactionService;
    @Autowired
    protected CommonAddressService commonAddressService;
    @Autowired
    private CommonTokenService commonTokenService;
    @Autowired
    protected StringRedisTemplate redisTemplate;
    @Autowired
    AdminWalletService adminWalletService;

    protected static volatile ExecutorService executorService;

    static {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("address-pool-%d").build();
        executorService = new ThreadPoolExecutor(10, 10, 10, TimeUnit.MINUTES,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.DiscardPolicy());
    }

    protected Boolean saveOrUpdate(BlockTransaction blockTransaction) {
        if (null == blockTransaction) {
            return null;
        }
        BlockTransaction trans = blockTransactionService.findOneBy("hash", blockTransaction.getHash());
        if (null == trans) {
            return blockTransactionService.saveTrans(blockTransaction);
        } else {
            blockTransactionService.updateTrans(trans, blockTransaction);
            return false;
        }
        //TODO 添加通用订单以及推送
    }

    protected CommonAddress isOurAddress(String from, String to) {
        if (StringUtils.isAnyBlank(from, to)) {
            return null;
        }
        CommonAddress cold = adminWalletService.isCold(from, to);
        if (null != cold) {
            //热钱包操作
            return cold;
        }
        //返回地址信息。充值和提现时返回用户地址，汇总和钱包操作则返回非用户地址（userId为0）
        CommonAddress fromAddress = commonAddressService.findOneBy("address", from);
        CommonAd