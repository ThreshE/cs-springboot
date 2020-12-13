package com.springboot.cs.common.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 返回统一基类
 */
@Data
@ApiModel
public class WebResult<T> extends BaseBean{

    @ApiModelProperty("返回信息")
    private String message;

    @ApiModelProperty("返回内容")
    private T data;

    @ApiModelProperty("返回结果")
    private Boolean result;

}