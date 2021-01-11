package com.mvc.cryptovault.common.util;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class BaseContextHandler {
    public static final String ADDR_LISTEN_ = "ADDR_LISTEN_";
    public static ThreadLocal<Map<String, Object>> threadLocal = new