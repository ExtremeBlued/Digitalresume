package com.mvc.cryptovault.common.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * common_pair
 */
@Table(name = "common_pair")
@Data
public class CommonPair implements Serializable {
    /**
     * 交易对id
     */
    @Id

    @Column(name = "id")
    private BigInteger id;

    /**
     * 交易对名称
     */
    @Column(name = "pair_name")
    private String pairName;

    /**
     * 基础货币令牌id
     */
    @Column(name = "base_token_id")
    private BigInteger baseTokenId;

    /**
     * 兑换货币id
     */
    @Column(name = "token_id")
    private BigInteger tokenId;

    /**
     * 基础货币令牌名称
     */
    @Column(name = "base_token_name")
    private String baseTokenName;

    private Integer status;

    /**
     * 兑换货币名称
     */
    @Column(name = "token_name")
    private String tokenName;

    /**
     * 手续费
     */
    @Column(name = "fee")
    private Floa