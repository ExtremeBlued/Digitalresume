package com.mvc.cryptovault.dashboard.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;

/**
 * 加密算法工具类
 * @author LDY20151214
 *
 */
public class EncryptionUtil {
    /*** 
     * MD5加密 生成32位md5码
     * @return 返回32位md5码
     */
    public static String md5(String