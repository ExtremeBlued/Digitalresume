package com.mvc.cryptovault.console.service;

import com.mvc.cryptovault.common.bean.AppUserAddress;
import com.mvc.cryptovault.common.bean.CommonAddress;
import com.mvc.cryptovault.common.bean.CommonToken;
import com.mvc.cryptovault.console.common.AbstractService;
import com.mvc.cryptovault.console.common.BaseService;
import com.mvc.cryptovault.console.dao.AppUserAddressMapper;
import com.mvc.cryptovault.console.dao.CommonAddressMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class AppUserAddressService extends AbstractService<AppUserAddress> implements BaseService<AppUserAddress> {

    @Autowired
    AppUserAddressMapper appUserAddressMapper;
    @Autowired
    CommonAddressMapper commonAddressMapper;
    @Autowired
    CommonTokenService commonTokenService;

    public String getAddress(BigInteger userId, BigInteger tokenId) {
        String key = "AppUserAddress".toUpperCase() + "_" + userId;
        if (redisTemplate.boundHashOps(key).size() == 0 || null == redisTemplate.boundHashOps(key).get(String.valueOf(tokenId))) {
            AppUserAddress condition = new AppUserAddress();
            condition.setUserId(userId);
            condition.setTokenId(tokenId);
   