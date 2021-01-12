package com.mvc.cryptovault.common.util;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class BaseContextHandler {
    public static final String ADDR_LISTEN_ = "ADDR_LISTEN_";
    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal();

    public BaseContextHandler() {
    }

    public static void set(String key, Object value) {
        Map<String, Object> map = (Map)threadLocal.get();
        if (map == null) {
            map = new HashMap(1);
            threadLocal.set(map);
        }

        ((Map)map).put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = (Map)threadLocal.get();
        if (map == null) {
            map = new HashMap(1);
            threadLocal.set(map);
        }

        return ((Map)map).get(key);
    }

    public static String getUserID() {
        Object va