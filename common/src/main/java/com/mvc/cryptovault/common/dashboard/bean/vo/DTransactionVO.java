package com.mvc.cryptovault.common.dashboard.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author qiyichen
 * @create 2018/11/20 15:52
 */
@Data
public class DTransactionVO implements Serializable {
    private static final long serialVersionUID = 8921086233008805528L;

    @ApiModelProperty("创建时间")
    private Long createdAt;

    @ApiModelProperty("更新时间")
    private Long updatedAt;

    @ApiModelProperty("交易对名")
    private String pairName;

    @ApiModelProperty("挂单数量")
    private BigDecimal value;

    @ApiModelProperty("成交数量")
    private BigDecimal deal;

    @ApiModelProperty("待成交数量")
    private BigDecimal surplu