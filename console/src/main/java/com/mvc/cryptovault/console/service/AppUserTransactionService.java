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
    AppUserService a