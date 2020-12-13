package com.springboot.cs.svo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class TestVO {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名称")
    private  String name;
}
