package com.springboot.cs.common.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分页基类
 */
@Data
@ApiModel
public class PageResult<T> extends BaseBean {

    private static final long serialVersionUID = 1L;

    /**
     * 总数
     */
    @ApiModelProperty("总数")
    private Integer count = 0;

    /**
     * 列表
     */
    @ApiModelProperty("列表集合")
    private List<T> list;



}
