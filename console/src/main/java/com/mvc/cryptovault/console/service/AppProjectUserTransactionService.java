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
      