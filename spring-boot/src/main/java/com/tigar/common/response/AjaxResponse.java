package com.tigar.common.response;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * OA研发接口数据返回格式模型
 */
public class AjaxResponse<T> implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static <T> AjaxResponse<T> getInstance() {
        return new AjaxResponse<>();
    }

    @JsonProperty("target_url")
    @ApiModelProperty(value = "报错接口URI", dataType = "String")
    private String targetUrl;

    @JsonProperty("is_authorized_request")
    @ApiModelProperty(value = "是否有调用权限", dataType = "Boolean")
    private Boolean authorizedRequest;

    @JsonProperty("is_success")
    @ApiModelProperty(value = "是否返回正确结果", dataType = "Boolean")
    private Boolean success;

    @JsonProperty("result")
    @ApiModelProperty(value = "正确结果", dataType = "Boolean")
    private T result;

    @JsonProperty("error_info")
    @ApiModelProperty(value = "错误信息", dataType = "ErrorInfo")
    private ErrorInfo errorInfo;

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public Boolean getAuthorizedRequest() {
        return authorizedRequest;
    }

    public void setAuthorizedRequest(Boolean authorizedRequest) {
        this.authorizedRequest = authorizedRequest;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }



    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
