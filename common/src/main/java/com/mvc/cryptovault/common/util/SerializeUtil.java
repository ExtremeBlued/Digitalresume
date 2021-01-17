package com.mvc.cryptovault.common.util;

import org.springframework.util.NumberUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author qiyichen
 * @create 2018/11/12 16:26
 */
public class SerializeUtil {

    public static <T> T list2Bean(List<String> keys, List<Object> values, Class<T> clazz) {
        if (keys.size() != values.size() || keys.size() == 0) {
            return null;
        }
        try {
            T obj = clazz.getDeclaredConstructor().newInstance();
            Field[] fs = clazz.getDeclared