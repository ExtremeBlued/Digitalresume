package com.mvc.cryptovault.common.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author qiyichen
 * @create 2018/11/8 16:44
 */
@Data
@ApiModel("参与的项目")
public class PurchaseVO {
    @ApiModelProperty("项目id")
    private BigInteger projectId;
    @ApiModelProperty("参与项目记录id")
    private BigInteger id;
    @ApiModelProperty("项目名称")
    private String 