package com.mvc.cryptovault.dashboard.service;

import com.mvc.cryptovault.common.util.BaseContextHandler;
import com.mvc.cryptovault.dashboard.feign.ConsoleRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * @author qiyichen
 * @create 2018/11/19 19:57
 */
@Service
public class BaseService