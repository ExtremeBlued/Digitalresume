package com.mvc.cryptovault.console.bean;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * bean
 *
 * @author qiyichen
 * @create 2018/6/23 14:11
 */
@Data
public class UsdtTransaction {

    private String txid;
    private String fee;
    private String sendingaddress;
    private String referenceaddress;
    private Boolean ismine;
    private Integer version;
 