package com.mvc.cryptovault.common.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * common_token_price
 */
@Table(name = "common_token_price")
@Data
public class CommonTokenPrice implements Serializable {
    /**
     * 令牌id
     */
    @Id
    @Column(name = "tok