package com.mvc.cryptovault.console.constant;

import java.math.BigInteger;

public interface BusinessConstant {

    /**
     * 上拉
     */
    Integer SEARCH_DIRECTION_UP = 0;
    /**
     * 下拉
     */
    Integer SEARCH_DIRECTION_DOWN = 1;
    /**
     * 项目等待购买
     */
    Integer APP_PROJECT_STATUS_WAIT = 0;
    /**
     * 项目订单前缀
     */
    String APP_PROJECT_ORDER_NUMBER = "APP_PROJECT_ORDER_NUMBER_";

    /**
     *  vrt
     */
    BigInteger BASE_TOKEN_ID_VRT = BigInteger.ONE;

    /**
     * 余额
     */
    BigInteger BASE_TOKEN_ID_BALANCE = BigInteger.valueOf(2);
    /**
     * ETH
     */
    BigInteger BASE_TOKEN_ID_ETH = BigInteger.valueOf(3);
    /**
     * USDT
     */
    BigInteger BASE_TOKEN_ID_USDT = BigInteger.valueOf(4);
    /**
     * 购买
   