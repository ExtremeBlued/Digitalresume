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
     * hash前缀
     */
    @Column(name = "link")
    private String link;

    /**
     * 令牌位数，代币使用
     */
    @Column(name = "token_decimal")
    private Integer tokenDecimal;

    /**
     * 合约类型（代币属性）
     */
    @Column(name = "token_contract_address")
    private String tokenContractAddress;

    /**
     * 排序id
     */
    @Column(name = "index_id")
    private Integer indexId;

    /**
     * 是否展示
     */
    @Column(name = "visible")
    private Integer visible;

    /**
     * 是否可冲提
     */
    @Column(name = "withdraw")
    private Integer withdraw;

    /**
     * 是否可冲提
     */
    @Column(name = "recharge")
    private Integer recharge;

    /**
     * 提现手续费（对用户，区块链手续费另行设置）
     */
    @Column(name = "fee")
    private Float fee;

    /**
     * 实际转账手续费（用于区块链转账)
     */
    @Column(name = "transafer_fee")
    private Float transaferFee;

    /**
     * 单笔提币下限
     */
    @Column(name = "withdraw_min")
    private BigDecimal withdrawMin;

    /**
     * 单笔提币上限
     */
    @Column(name = "withdraw_max")
    private BigDecimal withdrawMax;

    /**
     * 每日提币上限
     */
    @Column(name = "withdraw_day")
    private BigDecimal withdrawDay;

    /**
     * 删除标记位
 