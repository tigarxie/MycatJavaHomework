package com.tigar.common.response;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 校验属性详情
 */
public class ValidationErrorInfo implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @JsonProperty("message")
    @ApiModelProperty(value = "校验详情", dataType = "String")
    private String message;

    @JsonProperty("members")
    @ApiModelProperty(value = "错误字段", dataType = "List")
    private List<String> members;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
