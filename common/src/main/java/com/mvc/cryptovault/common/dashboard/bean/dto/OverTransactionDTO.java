package com.mvc.cryptovault.common.dashboard.bean.dto;

import com.mvc.cryptovault.common.bean.dto.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author qiyichen
 * @create 2018/11/20 16:07
 */
@Data
public class OverTransactionDTO extends PageDTO implements Serializable {

    private static final long serialVersionUID = 5630656349786332606L;
    @ApiModelProperty("交易对id")
    private BigInteger pairId;

    @ApiModelProperty("交易类型 1购买 2出