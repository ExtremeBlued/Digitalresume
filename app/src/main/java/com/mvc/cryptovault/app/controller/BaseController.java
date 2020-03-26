package com.mvc.cryptovault.app.controller;

import com.mvc.cryptovault.app.service.*;
import com.mvc.cryptovault.common.bean.vo.Result;
import com.mvc.cryptovault.common.util.BaseContextHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;

/**
 * @author qiyichen
 * @create 2018/11/6 17:11
 */
public class BaseController {

 