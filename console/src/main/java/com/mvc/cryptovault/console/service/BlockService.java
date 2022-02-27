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
import java.ut