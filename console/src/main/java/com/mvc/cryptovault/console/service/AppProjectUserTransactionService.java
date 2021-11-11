package com.mvc.cryptovault.console.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mvc.cryptovault.common.bean.AppProject;
import com.mvc.cryptovault.common.bean.AppProjectUserTransaction;
import com.mvc.cryptovault.common.bean.AppUser;
import com.mvc.cryptovault.common.bean.dto.ImportPartake;
import com.mvc.cryptovault.common.bean.dto.ProjectBuyDTO;
import com.mvc.cryptovault.common.bean.dto.ReservationDTO;
import com.mvc.cryptovault.common.bean.vo.ProjectBuyVO;
import com.mvc.cryptovault.common.bean.vo.PurchaseVO;
import com.mvc.cryptovault.common.dashboard.bean.dto.DProjectOrderDTO;
import com.mvc.cryptovault.common.dashboard.bean.vo.DProjectOrderVO;
import com.mvc.cryptovault.common.util.ConditionUtil;
import com.mvc.cryptovault.common.util.MessageConstants;
import com.mvc.cryptovault.console.common.AbstractService;
import com.mvc.cryptovault.console.common.BaseService;
import com.mvc.cryptovault.console.constant.BusinessConstant;
import com.mvc.cryptovault.console.dao.AppProjectUserTransactionMapper;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.NumberUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppProjectUserTransactionService extends AbstractService<AppProjectUserTransaction> implements BaseService<AppProjectUserTransaction> {

    @Autowired
    AppProjectUserTransactionMapper appProjectUserTransactionMapper;
    @Autowired
    AppProjectService appProjectService;
    @Autowired
    AppUserBalanceService appUserBalanceService;
    @Autowired
    AppUserService appUserService;
    @Autowired
    AppOrderService appOrderService;
    @Autowired
    AppOrderDetailService appOrderDetailService;
    @Autowired
    CommonPairService commonPairService;
    @Autowired
    CommonTokenService commonTokenService;

    public BigDecimal getUserBuyTotal(BigInteger userId, BigInteger project) {
        String key = "AppProjectUserTransaction".toUpperCase() + "_BALANCE_" + userId;
        if (redisTemplate.hasKey(key)) {
            String balance = (String) redisTemplate.boundHashOps(key).get(String.valueOf(project));
            if (StringUtils.isNotBlank(balance) && !"null".equals(balance)) {
                return NumberUtils.parseNumber(balance, BigDecimal.class);
            }
        }
        BigDecimal result = null;
        BigDecimal sum = appProjectUserTransactionMapper.sum(userId, project);
        if (null == sum) {
            result = BigDecimal.ZERO;
        } else {
            result = sum;
        }
        //余额记录永久保存
        redisTemplate.boundHashOps(key).put(String.valueOf(project), String.valueOf(result));
        return result;
    }

    public Boolean buy(BigInteger userId, BigInteger projectId, ProjectBuyDTO dto) {
        putAll(userId, false);
        Long time = System.currentTimeMillis();
        AppProject project = buyCheck(userId, projectId, dto);
        AppProjectUserTransaction appProjectUserTransaction = saveAppProjectUserTransaction(userId, projectId, dto, time, project);
        updateCache(userId, dto, project, appProjectUserTransaction);
        appProjectUserTransaction.setResult(0);
        appOrderService.saveOrder(appProjectUserTransaction, project);
        return true;
    }

    private void updateCache(BigInteger userId, ProjectBuyDTO dto, AppProject project, AppProjectUserTransaction appProjectUserTransaction) {
        appUserBalanceService.updateBalance(userId, project.getBaseTokenId(), BigDecimal.ZERO.subtract(appProjectUserTransaction.getPayed()));
        //TODO 异步发送推送,修改余额,生成统一订单,添加到getReservation缓存列表
        String balanceKey = "AppProjectUserTransaction".toUpperCase() + "_BALANCE_" + userId;
        redisTemplate.boundHashOps(balanceKey).increment(String.valueOf(appProjectUserTransaction.getProjectId()), Double.valueOf(String.valueOf(dto.getValue())));
        String key = "AppProjectUserTransaction".toUpperCase() + "_INDEX_" + userId;
        String listKey = "AppProjectUserTransaction".toUpperCase() + "_USER_" + userId;
        redisTemplate.boundHashOps(key).put(String.valueOf(appProjectUserTransaction.getId()), String.valueOf(appProjectUserTransaction.getIndex()));
        redisTemplate.delete(listKey);
        putAll(userId, true);
    }

    @NotNull
    private AppProjectUserTransaction saveAppProjectUserTransaction(BigInteger userId, BigInteger projectId, ProjectBuyDTO dto, Long time, AppProject project) {
        AppProjectUserTransaction appProjectUserTransaction = new AppProjectUserTransaction();
        appProjectUserTransaction.setCreatedAt(time);
        appProjectUserTransaction.setUpdatedAt(time);
        appP