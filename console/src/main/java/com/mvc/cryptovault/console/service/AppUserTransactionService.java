package com.mvc.cryptovault.console.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mvc.cryptovault.common.bean.*;
import com.mvc.cryptovault.common.bean.dto.MyTransactionDTO;
import com.mvc.cryptovault.common.bean.dto.OrderDTO;
import com.mvc.cryptovault.common.bean.dto.PageDTO;
import com.mvc.cryptovault.common.bean.dto.TransactionBuyDTO;
import com.mvc.cryptovault.common.bean.vo.MyOrderVO;
import com.mvc.cryptovault.common.bean.vo.OrderVO;
import com.mvc.cryptovault.common.dashboard.bean.dto.DTransactionDTO;
import com.mvc.cryptovault.common.dashboard.bean.dto.OverTransactionDTO;
import com.mvc.cryptovault.common.dashboard.bean.vo.DTransactionVO;
import com.mvc.cryptovault.common.dashboard.bean.vo.OverTransactionVO;
import com.mvc.cryptovault.common.util.ConditionUtil;
import com.mvc.cryptovault.common.util.MessageConstants;
import com.mvc.cryptovault.console.common.AbstractService;
import com.mvc.cryptovault.console.common.BaseService;
import com.mvc.cryptovault.console.constant.BusinessConstant;
import com.mvc.cryptovault.console.dao.AppUserTransactionMapper;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO 只缓存前几页数据，后几页数据短生命周期缓存和搜索引擎结合.暂时直接走数据库
 */
@Service
public class AppUserTransactionService extends AbstractService<AppUserTransaction> implements BaseService<AppUserTransaction> {
    private final String KEY_PREFIX = "AppUserTransaction".toUpperCase() + "_";

    @Autowired
    CommonPairService commonPairService;
    @Autowired
    AppUserService appUserService;
    @Autowired
    AppUserBalanceService appUserBalanceService;
    @Autowired
    CommonTokenPriceService commonTokenPriceService;
    @Autowired
    CommonTokenControlService commonTokenControlService;
    @Autowired
    AppOrderService appOrderService;
    @Autowired
    TokenVolumeService tokenVolumeService;
    @Autowired
    AppUserTransactionMapper appUserTransactionMapper;
    @Autowired
    AppProjectService appProjectService;

    public List<OrderVO> getTransactions(OrderDTO dto) {
        Condition condition = new Condition(AppUserTransaction.class);
        Example.Criteria criteria = condition.createCriteria();
        ConditionUtil.andCondition(criteria, "parent_id = ", 0);
        ConditionUtil.andCondition(criteria, "status = ", 0);
        ConditionUtil.andCondition(criteria, "transaction_type = ", dto.getTransactionType());
        ConditionUtil.andCondition(criteria, "pair_id =", dto.getPairId());
        PageHelper.startPage(1, dto.getPageSize());
        if (2 == dto.getTransactionType()) {
            PageHelper.orderBy("price asc,id asc");
        } else {
            PageHelper.orderBy("price desc,id desc");
        }
        if (dto.getType() == 0 && null != dto.getId()) {
            ConditionUtil.andCondition(criteria, "id > ", dto.getId());
        } else if (dto.getType() == 1 && null != dto.getId()) {
            AppUserTransaction trans = appUserTransactionMapper.selectByPrimaryKey(dto.getId());
            if(null != trans){
                if (2 == dto.getTransactionType()) {
                    ConditionUtil.andCondition(criteria, String.format("CONCAT(price, id) > '%s'", trans.getPrice() + "" + String.format("%012d", trans.getId())));
                } else {
                    ConditionUtil.andCondition(criteria, String.format("CONCAT(price, id) < '%s'", trans.getPrice() + "" + String.format("%012d", trans.getId())));
                }
            }
        }
        List<AppUserTransaction> list = findByCondition(condition);
        List<OrderVO> result = new ArrayList<>(list.size());
        list.forEach(obj -> {
            OrderVO vo = new OrderVO();
            AppUser user = appUserService.findById(obj.getUserId());
            vo.setHeadImage(user.getHeadImage());
            vo.setLimitValue(obj.getValue().subtract(obj.getSuccessValue()));
            vo.setNickname(user.getNickname());
            vo.setTotal(obj.getValue());
            vo.setTransactionType(obj.getTransactionType());
            vo.setId(obj.getId());
            vo.setPrice(obj.getPrice());
            result.add(vo);
        });
        return result;
    }

    public List<MyOrderVO> getUserTransactions(BigInteger userId, MyTransactionDTO dto) {
        Condition condition = new Condition(AppUserTransaction.class);
        Example.Criteria criteria = condition.createCriteria();
        ConditionUtil.andCondition(criteria, "pair_id = ", dto.getPairId());
        if (null == dto.getStatus()) {
            ConditionUtil.andCondition(criteria, "status in (0, 1)");
        } else {
            ConditionUtil.andCondition(criteria, "status = ", dto.getStatus());
        }
        ConditionUtil.andCondition(criteria, "transaction_type = ", dto.getTransactionType());
        ConditionUtil.andCondition(criteria, "user_id = ", userId);
        PageHelper.startPage(1, dto.getPageSize());
        PageHelper.orderBy("id desc");
        if (dto.getType() == 0 && null != dto.getId()) {
            ConditionUtil.andCondition(criteria, "id > ", dto.getId());
        } else if (dto.getType() == 1 && null != dto.getId()) {
            ConditionUtil.andCondition(criteria, "id < ", dto.getId());
        }
        List<AppUserTransaction> list = findByCondition(condition);
        List<MyOrderVO> result = new ArrayList<>(list.size());
        list.forEach(obj -> {
            MyOrderVO vo = new MyOrderVO();
            AppUser user = appUserService.findById(obj.getTargetUserId());
            BeanUtils.copyProperties(obj, vo);
            vo.setDeal(obj.getValue());
            vo.setNickname(null == user ? "" : user.getNickname());
            result.add(vo);
        });
        return result;
    }

    //TODO 步骤较多,异步化
    public void buy(BigInteger userId, TransactionBuyDTO dto) {
        dto.setId(BigInteger.ZERO.equals(dto.getId()) ? null : dto.getId());
        CommonPair pair = commonPairService.findById(dto.getPairId());
        CommonTokenPrice tokenPrice = commonTokenPriceService.findById(pair.getTokenId());
        CommonTokenControl token = commonTokenControlService.findById(pair.getTokenId());
        AppUserTransaction targetTransaction = null;
        //校验开关是否开启
        Assert.isTrue(null != token && token.getTransactionStatus() == 1, MessageConstants.getMsg("TRANS_STATUS_CLOSE"));
        //校验余额是否足够
        checkBalance(userId, dto, pair);
        //校验浮动范围是否正确
        checkPrice(dto, pair, tokenPrice);
        if (null != dto.getId() && !dto.getId().equals(BigInteger.ZERO)) {
            //校验订单信息是否输入错误
            targetTransaction = mapper.selectByPrimaryKey(dto.getId());
            checkDto(targetTransaction, dto);
            //校验可购买量是否足够
            checkValue(dto, targetTransaction);
        }
        saveAll(userId, dto, targetTransaction, pair);
    }

    private void checkDto(AppUserTransaction targetTransaction, TransactionBuyDTO dto) {
        Assert.isTrue(!targetTransaction.getTransactionType().equals(dto.getTransactionType()), MessageConstants.getMsg("TRANS_MSG_ERROR"));
        Assert.isTrue(dto.getPrice().compareTo(targetTransaction.getPrice()) == 0, MessageConstants.getMsg("TRANS_MSG_ERROR"));
    }

    public void saveAll(BigInteger userId, TransactionBuyDTO dto, AppUserTransaction targetTransaction, CommonPair pair) {
        Long time = System.currentTimeMillis();
        AppUserTransaction transaction = getAppUserTransaction(userId, dto, time);
        updateBalance(userId, dto, pair);
        if (null == dto.getId() || dto.getId().equals(BigIntege