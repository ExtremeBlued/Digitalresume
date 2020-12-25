package com.mvc.cryptovault.common.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author qiyichen
 * @create 2018/11/8 16:30
 */
@Data
@ApiModel("项目简略信息")
public class ProjectSimpleVO {

    @ApiModelProperty("项目id")
    private BigInteger projectId;
    @ApiModelProperty("项目状态0即将开始 1进行中 2已结束")
    private Integer status;
    @ApiModelProperty("项目名称")
    private String projectName;
    @ApiModelProperty("限购")
    private BigDecimal projectLimit;
    @ApiModelProperty("基础货币缩写")
    private String baseTokenName;
  