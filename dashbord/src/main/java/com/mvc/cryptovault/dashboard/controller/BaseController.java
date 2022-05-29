package com.mvc.cryptovault.dashboard.controller;

import com.mvc.cryptovault.common.constant.RedisConstant;
import com.mvc.cryptovault.common.util.BaseContextHandler;
import com.mvc.cryptovault.dashboard.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;

/**
 * @author qiyichen
 * @create 2018/11/6 17:11
 */
public class BaseController {

    @Autowired
    protected StringRedisTemplate redisTemplate;
    protected RestTemplate restTemplate = new RestTemplate();

    @Autowired
    AdminService adminService;
    @Autowired
    BlockService blockService;
    @Autowired
    ProjectService projectService;
    @Autowired
    TokenService tokenService;
    @Autowired
    TransactionService tra