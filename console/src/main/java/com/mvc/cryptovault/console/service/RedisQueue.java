package com.mvc.cryptovault.console.service;

import com.mvc.cryptovault.console.constant.BusinessConstant;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RedisQueue {

    private BoundListOperations<String, String> listOperations;

    private static Lock lock = new ReentrantLock();

    private RedisTemplate redisTemplate;

    public RedisQueue(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        listOperations = redisTemplate.boundListOps(BusinessConstant.REDIS_QUEUE);
    }

    /**
     * blocking 一直阻塞直到队列里边有数据
     * remove and get last item from queue:BRPOP
     *
     * @return
     */
    public String ta