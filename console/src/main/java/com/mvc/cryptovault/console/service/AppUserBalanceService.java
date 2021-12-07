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
public class AppUserBalanceService extends Ab