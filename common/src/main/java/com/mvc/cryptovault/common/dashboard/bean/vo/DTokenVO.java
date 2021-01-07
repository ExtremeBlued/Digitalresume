package com.mvc.cryptovault.common.dashboard.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author qiyichen
 * @create 2018/11/20 16:19
 */
@Data
public class DTokenVO implements Serializable {
    private static final long serialVersionUID = -8746068592195617206L;

    @ApiModelProperty("令牌名称")
    private String tokenName;
    @ApiModelProperty("令牌id")
    private BigInteger tokenId;
    @ApiModelProperty("是否展示")
    private Integer visible;
    @ApiModelProperty("是否可提现")
    private Integer withdraw;
    @ApiModelProperty("是否可充值")
 