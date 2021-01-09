package com.mvc.cryptovault.common.dashboard.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author qiyichen
 * @create 2018/11/20 16:04
 */
@Data
public class OverTransactionVO implements Serializable {
    private static final long serialVersionUID = -7869572159975149575L;

    @ApiModelProperty("创建时间")
    private Long createdAt;

    @ApiModelProperty("更新时间(最终成交时间)")
    private Long updatedAt;

    @ApiModelProperty("订单号")
    private String orderNum