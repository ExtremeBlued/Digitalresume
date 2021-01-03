package com.mvc.cryptovault.common.dashboard.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author qiyichen
 * @create 2018/11/20 17:23
 */
@Data
public class DProjectOrderVO implements Serializable {

    private static final long serialVersionUID = -2959375044042658750L;
    @ApiModelProperty("创建时间")
    private Long createdAt;
    @ApiModelProperty("订单号")
    private String orderNumber;
    @ApiModelProperty("项目名称")
    private String projectName;
    @ApiModelProperty("项目id")
    private String projectId;
    @ApiModelProperty("用户手机号")
    private String cellphone;
   