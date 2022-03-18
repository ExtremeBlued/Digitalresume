package com.mvc.cryptovault.console.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * 暂时只用来处理区跨链订单,其他需求后续修改
 */
public class OrderSendRedisConsumer extends Thread {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private RedisTaskContainer container;
    private Consumer<