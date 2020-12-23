package com.mvc.cryptovault.common.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigInteger;

/**
 * @author qiyichen
 * @create 2018/11/8 14:27
 */
@Data
@ApiModel("消息")
public class MessageVO {

    @ApiModelProperty("消息id")
    private BigInteger id;

    @ApiModelPro