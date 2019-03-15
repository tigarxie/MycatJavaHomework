package com.tigar.common.response;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;


/**
 * OA研发接口数据返回格式模型（列表）
 */
public class AjaxPageResponse<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static <T> AjaxPageResponse<T> getInstance() {
        return new AjaxPageResponse<>();
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
    @ApiModelProperty(value = "正确结果", dataType = "Results")
    private Results<T> result;

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

    public Results<T> getResult() {
        return result;
    }

    public void setResult(Results<T> result) {
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

    public static class Results<T> implements Serializable {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        @JsonProperty("total_count")
        @ApiModelProperty(value = "数据总条数", dataType = "Integer")
        private Integer totalCount;

        @JsonProperty("items")
        @ApiModelProperty(value = "列表信息", dataType = "List")
        private List<T> items;

        public Integer getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
        }

        public List<T> getItems() {
            return items;
        }

        public void setItems(List<T> items) {
            this.items = items;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }
}
