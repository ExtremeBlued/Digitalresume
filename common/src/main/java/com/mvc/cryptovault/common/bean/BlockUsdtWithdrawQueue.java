package com.mvc.cryptovault.common.bean;

import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author qiyichen
 * @create 2018/12/14 10:45
 */
@Data
public class BlockUsdtWithdrawQueue {
    @Id
    private BigInteger id;
    private String orderId;
    private String fromAddress;
    