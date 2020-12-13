package com.springboot.cs.vo.sys;

import com.springboot.cs.common.bean.BaseBean;
import com.springboot.cs.common.bean.BaseSearchVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SysUserVO extends BaseBean{

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("用户名称")
    private String userName;
}
