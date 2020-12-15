package com.mvc.cryptovault.common.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author qiyichen
 * @create 2018/11/7 14:36
 */
@Data
@ApiOperation("转账输入类")
public class TransactionDTO {

    @ApiModelProperty("地址")
    @NotNull(message = "{ADDRESS_NULL}")
    pr