package com.springboot.cs.common.bean;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 基础类
 */
@Data
@ApiModel
public class BaseBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("语言")
    private String language;

    //小程序 / PC(H5)
    @ApiModelProperty("系统类型")
    private String systemType;

    @ApiModelProperty("幂等id")
    private String idempotentId;


    /**
     * 将对象转换为JSON字符串<br>
     * DisableCircularReferenceDetect:关闭引用
     *
     * @return
     */
    public String toJSONString() {
        return JSONObject.toJSONString(this, SerializerFeature.WriteMapNullValue,
                SerializerFeature.DisableCircularReferenceDetect);
    }
}
