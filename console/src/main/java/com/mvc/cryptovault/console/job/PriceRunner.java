package com.mvc.cryptovault.console.job;

import com.mvc.cryptovault.common.bean.TokenVolume;
import com.mvc.cryptovault.common.constant.RedisConstant;
import com.mvc.cryptovault.console.service.CommonTokenControlNextService;
import com.mvc.cryptovault.console.service.CommonTokenControlService;
import com.mvc.cryptovault.console.service.CommonTokenPriceService;
import com.mvc.cryptovault.console.service.TokenVolumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author qiyichen
 * @create 2018/12/10 16:55
 */
@Component
public class PriceRunner implements CommandLineRunner {

    @Autowired
    TokenVolumeService tokenVolumeService;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired