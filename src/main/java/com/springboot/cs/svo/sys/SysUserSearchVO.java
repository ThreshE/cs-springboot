package com.springboot.cs.svo.sys;

import com.springboot.cs.common.bean.BaseSearchVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel
public class SysUserSearchVO extends BaseSearchVO{

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("用户名称")
    private String userName;

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
