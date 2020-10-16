
package com.mvc.cryptovault.common.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * common_token_control
 */
@Table(name = "common_token_control")
@Data
public class CommonTokenControl implements Serializable {
    /**
     * 令牌id
     */
    @Id
    @Column(name = "token_id")
    private BigInteger tokenId;

    /**
     * 最小涨停百分比
     */
    @Column(name = "increase_min")
    private Float increaseMin;

    /**
     * 最大涨停百分比
     */
    @Column(name = "increase_max")
    private Float increaseMax;

    /**
     * 最小跌停百分比
     */
    @Column(name = "decrease_min")
    private Float decreaseMin;

    /**
     * 最大跌停百分比
     */
    @Column(name = "decrease_max")
    private Float decreaseMax;

    /**
     * 购买定价下限
     */
    @Column(name = "buy_min")
    private Float buyMin;

    /**
     * 购买定价上限
     */
    @Column(name = "buy_max")
    private Float buyMax;

    /**
     * 出售定价下限
     */
    @Column(name = "sell_min")
    private Float sellMin;

    /**
     * 出售定价上限
     */
    @Column(name = "sell_max")
    private Float sellMax;

    /**
     * 价格波动基数
     */
    @Column(name = "price_base")
    private BigDecimal priceBase;

    /**
     * 最小购买量
     */
    @Column(name = "min_limit")
    private BigDecimal minLimit;

    /**
     * 下个基准价格
     */
    @Column(name = "next_price")
    private BigDecimal nextPrice;

    /**
     * 涨跌波动最小百分比
     */
    @Column(name = "wave_min")
    private Float waveMin;

    /**
     * 涨跌波动最大百分比
     */
    @Column(name = "wave_max")
    private Float waveMax;


    private BigDecimal startPrice;

    private Integer startStatus;

    private Integer transactionStatus;

    /**
     * common_token_control
     */
    private static final long serialVersionUID = 1L;

    /**
     * 令牌id
     *
     * @return token_id 令牌id
     */
    public BigInteger getTokenId() {
        return tokenId;
    }

    /**
     * 令牌id
     *
     * @param tokenId 令牌id
     */
    public void setTokenId(BigInteger tokenId) {
        this.tokenId = tokenId;
    }

    /**
     * 最小涨停百分比
     *
     * @return increase_min 最小涨停百分比
     */
    public Float getIncreaseMin() {
        return increaseMin;
    }

    /**
     * 最小涨停百分比
     *
     * @param increaseMin 最小涨停百分比
     */
    public void setIncreaseMin(Float increaseMin) {
        this.increaseMin = increaseMin;
    }

    /**
     * 最大涨停百分比
     *
     * @return increase_max 最大涨停百分比
     */
    public Float getIncreaseMax() {
        return increaseMax;
    }

    /**
     * 最大涨停百分比
     *
     * @param increaseMax 最大涨停百分比
     */
    public void setIncreaseMax(Float increaseMax) {
        this.increaseMax = increaseMax;
    }

    /**
     * 最小跌停百分比
     *
     * @return decrease_min 最小跌停百分比
     */
    public Float getDecreaseMin() {
        return decreaseMin;
    }

    /**
     * 最小跌停百分比
     *
     * @param decreaseMin 最小跌停百分比
     */
    public void setDecreaseMin(Float decreaseMin) {
        this.decreaseMin = decreaseMin;
    }

    /**
     * 最大跌停百分比
     *
     * @return decrease_max 最大跌停百分比
     */
    public Float getDecreaseMax() {
        return decreaseMax;
    }

    /**
     * 最大跌停百分比
     *
     * @param decreaseMax 最大跌停百分比
     */
    public void setDecreaseMax(Float decreaseMax) {
        this.decreaseMax = decreaseMax;
    }

    /**
     * 购买定价下限
     *
     * @return buy_min 购买定价下限
     */
    public Float getBuyMin() {
        return buyMin;
    }

    /**
     * 购买定价下限
     *
     * @param buyMin 购买定价下限
     */
    public void setBuyMin(Float buyMin) {
        this.buyMin = buyMin;
    }

    /**