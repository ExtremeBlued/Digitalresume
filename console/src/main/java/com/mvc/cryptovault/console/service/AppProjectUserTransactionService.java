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
        appProjectUserTransaction.setPairId(project.getPairId());
        appProjectUserTransaction.setProjectId(projectId);
        AppProjectUserTransaction temp = new AppProjectUserTransaction();
        temp.setUserId(userId);
        appProjectUserTransaction.setIndex(appProjectUserTransactionMapper.selectCount(temp));
        appProjectUserTransaction.setUserId(userId);
        appProjectUserTransaction.setResult(BusinessConstant.APP_PROJECT_STATUS_WAIT);
        appProjectUserTransaction.setValue(dto.getValue());
        appProjectUserTransaction.setProjectOrderNumber(getOrderNumber());
        appProjectUserTransaction.setSuccessPayed(BigDecimal.ZERO);
        appProjectUserTransaction.setSuccessValue(BigDecimal.ZERO);
        //花费金额=购买数量*货币比值
        BigDecimal balanceCost = dto.getValue().multiply(BigDecimal.valueOf(project.getRatio()));
        appProjectUserTransaction.setPayed(balanceCost);
        appProjectUserTransactionMapper.insert(appProjectUserTransaction);
        return appProjectUserTransaction;
    }

    private AppProject buyCheck(BigInteger userId, BigInteger projectId, ProjectBuyDTO dto) {
        AppUser user = appUserService.findById(userId);
        Assert.isTrue(user.getTransactionPassword().equalsIgnoreCase(dto.getPassword()), MessageConstants.getMsg("USER_TRANS_PASS_WRONG"));
        AppProject project = appProjectService.findById(projectId);
        ProjectBuyVO balance = appUserBalanceService.getBalance(userId, project);
        Assert.isTrue(balance.getBalance().compareTo(dto.getValue().multiply(NumberUtils.parseNumber(String.valueOf(project.getRatio()), BigDecimal.class))) >= 0, MessageConstants.getMsg("INSUFFICIENT_BALANCE"));
        Assert.isTrue(balance.getLimitValue().subtract(dto.getValue()).compareTo(BigDecimal.ZERO) >= 0, MessageConstants.getMsg("PROJECT_LIMIT_OVER"));
        Assert.isTrue(dto.getValue().subtract(balance.getProjectMin()).compareTo(BigDecimal.ZERO) >= 0, MessageConstants.getMsg("PROJECT_LIMIT_OVER"));
        return project;
    }

    public List<PurchaseVO> getReservation(BigInteger userId, ReservationDTO reservationDTO) {
        List<AppProjectUserTransaction> transList = null;
        List<PurchaseVO> result = new ArrayList<>(10);
        if (StringUtils.isNotBlank(reservationDTO.getProjectName())) {
            String ids = appProjectService.findIdsByName(reservationDTO.getProjectName());
            if (StringUtils.isBlank(ids)) {
                return new ArrayList<>(0);
            }
            //按项目名搜索不分页
            transList = getTransByProjectIds(userId, ids);
        } else {
            transList = getAppProjectUserTransactionsCache(userId, reservationDTO);
            if (transList == null) return null;
        }
        for (int i = 0; i < transList.size(); i++) {
            PurchaseVO vo = new PurchaseVO();
            AppProjectUserTransaction appProjectUserTransaction = transList.get(i);
            AppProject project = appProjectService.findById(appProjectUserTransaction.getProjectId());
            vo.setCreatedAt(appProjectUserTransaction.getCreatedAt());
            vo.setId(appProjectUserTransaction.getId());
            vo.setPrice(appProjectUserTransaction.getValue().multiply(new BigDecimal(project.getRatio())));
            vo.setProjectId(appProjectUserTransaction.getProjectId());
            vo.setProjectName(project.getProjectName());
            vo.setProjectOrderId(appProjectUserTransaction.getProjectOrderNumber());
            vo.setValue(appProjectUserTransaction.getValue());
            vo.setStopAt(project.getStopAt());
            vo.setReservationType(appProjectUserTransaction.getResult());
            vo.setReleaseValue(project.getReleaseValue());
            vo.setRatio(project.getRatio());
            vo.setTokenId(project.getTokenId());
            vo.setTokenName(project.getTokenName());
            vo.setBaseTokenName(project.getBaseTokenName());
            vo.setSuccessPayed(appProjectUserTransaction.getSuccessPayed());
            vo.setSuccessValue(appProjectUserTransaction.getSuccessValue());
            vo.setPublishAt(project.getPublishAt());
            result.add(vo);
        }
        return result;
    }

    private