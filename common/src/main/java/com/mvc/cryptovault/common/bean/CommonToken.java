package com.mvc.cryptovault.common.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * common_token
 */
@Table(name = "common_token")
@Data
public class CommonToken implements Serializable {
    /**
     * 
     */
    @Id

    @Column(name = "id")
    private BigInteger id;

    /**
     * 令牌名称
     */
    @Column(name = "token_name")
    private String tokenName;

    /**
     * 令牌中文
     */
    @Column(name = "token_cn_name")
    private String tokenCnName;

    /**
     * 令牌英文
     */
    @Column(name = "token_en_name")
    private String tokenEnName;

    /**
     * 令牌图片地址
     */
    @Column(name = "token_image")
    private String tokenImage;

    /**
     * 令牌类型，如ETH,BTC
     */
    @Column(name = "token_type")
    private String tokenType;

    /**