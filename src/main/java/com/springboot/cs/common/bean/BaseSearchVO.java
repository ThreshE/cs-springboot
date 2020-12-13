package com.springboot.cs.common.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 查询基础类
 */
@ApiModel
public class BaseSearchVO extends BaseBean{


    private static final long serialVersionUID = -1298454457291675035L;

    /**
     * 页面大小 默认为20
     */
    @ApiModelProperty("页面大小")
    private Integer pageSize = 20;

    /**
     * 页码 默认为第一页
     */
    @ApiModelProperty("页数")
    private Integer pageNo = 1;

    /**
     * 记录查询开始
     */
    @ApiModelProperty("暫不需要")
    @JsonIgnore
    private int  start  = 0;

    /**
     * 记录查询结束
     */
    @ApiModelProperty("暫不需要")
    @JsonIgnore
    private int  end  = 0;


    /** 获取开始条数*/
    public int getStart(){

        if(pageNo<=0){
            pageNo = 1;
        }
        start = (pageNo-1)*pageSize;
        return start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getEnd(){

        if(pageNo<=0){
            pageNo = 1;
        }
        end = pageNo*pageSize;
        return end;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

}
