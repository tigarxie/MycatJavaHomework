package com.tigar.common.response;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 错误信息
 */
public class ErrorInfo implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @JsonProperty("code")
    @ApiModelProperty(value = "错误码", dataType = "Long")
    private Integer code;

    @JsonProperty("msg")
    @ApiModelProperty(value = "错误信息", dataType = "String")
    private String msg;

    @JsonProperty("details")
    @ApiModelProperty(value = "错误堆栈", dataType = "List")
    private List<String> details;

    @JsonProperty("validation_error_info")
    @ApiModelProperty(value = "校验属性详情", dataType = "ValidationErrorInfo")
    private ValidationErrorInfo validationErrorInfo;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public ValidationErrorInfo getValidationErrorInfo() {
        return validationErrorInfo;
    }

    public void setValidationErrorInfo(ValidationErrorInfo validationErrorInfo) {
        this.validationErrorInfo = validationErrorInfo;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
