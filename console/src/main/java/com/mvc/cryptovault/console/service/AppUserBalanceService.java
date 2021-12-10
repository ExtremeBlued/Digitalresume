package com.mvc.cryptovault.console.service;

import com.github.pagehelper.PageHelper;
import com.mvc.cryptovault.common.bean.AppProject;
import com.mvc.cryptovault.common.bean.AppUserBalance;
import com.mvc.cryptovault.common.bean.CommonToken;
import com.mvc.cryptovault.common.bean.CommonTokenPrice;
import com.mvc.cryptovault.common.bean.dto.AssertVisibleDTO;
import com.mvc.cryptovault.common.bean.vo.ProjectBuyVO;
import com.mvc.cryptovault.common.bean.vo.TokenBalanceVO;
import com.mvc.cryptovault.console.common.AbstractService;
import com.mvc.cryptovault.console.common.BaseService;
import com.mvc.cryptovault.console.constant.BusinessConstant;
import com.mvc.cryptovault.console.dao.AppUserBalanceMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Service
public class AppUserBalanceService extends AbstractService<AppUserBalance> implements BaseService<AppUserBalance> {

    @Autowired
    AppUserBalanceMapper appUserBalanceMapper;
    @Autowired
    AppProjectUserTransactionService appProjectUserTransactionService;
    @Autowired
    CommonTokenService commonTokenService;
    @Autowired
    CommonTokenPriceService commonTokenPriceService;
    @Autowired
    AppOrderService appOrderService;

    private Comparator comparator = new Comparator<TokenBalanceVO>() {
        @Override
        public int compare(TokenBalanceVO o1, TokenBalanceVO o2) {
            return o1.getTokenId().compareTo(o2.getTokenId());
        }
    };

    public ProjectBuyVO getBalance(BigInteger userId, AppProject appProject) {
        ProjectBuyVO vo = new ProjectBuyVO();
        vo.setBalance(getBalanceByTokenId(userId, appProject.getBaseTokenId()));
        BigDecimal userBuyTotal = appProjectUserTransactionService.getUserBuyTotal(userId, appProject.getId());
        BigDecimal limit = appProject.getProjectLimit().subtract(userBuyTotal);
        vo.setLimitValue(limit);
        vo.setProjectMin(null == appProject.getProjectMin() ? BigDecimal.ZERO : appProject.getProjectMin());
        return vo;
    }

    public BigDecimal getBalanceByTokenId(BigInteger userId, BigInteger tokenId) {
        String key = "AppUserBalance".toUpperCase() + "_" + userId;
        if (redisTemplate.hasKey(key)) {
            String balance = (String) redisTemplate.boundHashOps(key).get(String.valueOf(tokenId));
            if (StringUtils.isNotBlank(balance)) {
                BigDecimal value = NumberUtils.parseNumber(balance.split("#")[1], BigDecimal.class);
                return value;
            }
        }
        BigDecimal result = null;
        AppUserBalance userBalance = getAppUserBalance(userId, tokenId);
        if (null == userBalance) {
            result = BigDecimal.ZERO;
        } else {
            result = userBalance.getBalance();
        }
        redisTemplate.boundHashOps(key).put(String.valueOf(tokenId), userBalance.getVisible() + "#" + String.valueOf(result));
        return result;
    }

    public void updateBalance(BigInteger userId, BigInteger baseTokenId, BigDecimal value) {
        AppUserBalance userBalance = getAppUserBalance(userId, baseTokenId);
        if (null == userBalance && !baseTokenId.equals(BigInteger.ZERO)) {
            userBalance = new AppUserBalance();
            userBalance.setBalance(BigDecimal.ZERO);
            userBalance.setTokenId(baseTokenId);
            userBalance.setUserId(userId);
            userBalance.setVisible(1);
            save(userBalance);
        }
        String key = "AppUserBalance".toUpperCase() + "_" + userId;
        appUserBalanceMapper.updateBalance(userId, baseTokenId, value);
        userBalance = getAppUserBalance(userId, baseTokenId);
        if (null != userBalance) {
            redisTemplate.boundHashOps(key).put(String.valueOf(baseTokenId), userBalance.getVisible() + "#" + String.valueOf(userBalance.getBalance()));
        }
    }

    private AppUserBalance getAppUserBalance(BigInteger userId, BigInteger baseTokenId) {
        AppUserBalance appUserBalance = new AppUserBalance();
        appUserBalance.setTokenId(baseTokenId);
        appUserBalance.setUserId(userId);
        PageHelper.clearPage();
        AppUserBalance balance = appUserBalanceMapper.selectOne(appUserBalance);
        if (null == balance) {
            balance = new AppUserBalance();
            balance.setVisible(0);
            balance.setBalance(BigDecimal.ZERO);
            balance.setTokenI