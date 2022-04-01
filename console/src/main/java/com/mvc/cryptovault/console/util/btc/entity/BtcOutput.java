package com.mvc.cryptovault.console.util.btc.entity;

import com.neemre.btcdcli4j.core.domain.Output;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * output can not be Serializable
 * @author qiyichen
 * @create 2018/12/12 16:27
 */
@Data
public class BtcOutput implements Serializable {

    private String address;
    private String account;
    private String scriptPubKey;
    private String redeemScript;
    private BigDecimal amount;
    private Integer confirmations;
    private Boolean spendable;
    private String txId;
    private Integer vOut;

    public BtcOutput() {
    }
    public 