package com.tigar.common.utils;

import com.tigar.common.response.AjaxPageResponse;
import com.tigar.common.response.AjaxResponse;
import com.tigar.common.response.ErrorInfo;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * @author tigar
 * @date 2019/2/1.
 */
public class AjaxUtil {

    public static <T> AjaxResponse<T> responseSuccess(T data) {
        AjaxResponse<T> response = new AjaxResponse<T>();
        response.setAuthorizedRequest(true);
        response.setSuccess(true);
        response.setResult(data);
        return response;
    }


    public static <T> AjaxResponse<T> responseError(String errMessage) {
        AjaxResponse<T> response = new AjaxResponse<T>();
        response.setAuthorizedRequest(true);
        response.setSuccess(false);
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setMsg(errMessage);
        response.setErrorInfo(errorInfo);
        return response;
    }

    public static <T> AjaxResponse<T> responseError(BindingResult bindingResult) {
        AjaxResponse<T> response = new AjaxResponse<T>();
        response.setAuthorizedRequest(true);
        response.setSuccess(false);
        response.setErrorInfo(ErrorInfoUtil.valueOf(bindingResult));
        return response;
    }


    public static <T> AjaxPageResponse<T> pageResponseSuccess(List<T> dataList, int totalCount) {
        AjaxPageResponse<T> response = new AjaxPageResponse<T>();
        response.setAuthorizedRequest(true);
        response.setSuccess(true);
        AjaxPageResponse.Results<T> results = new AjaxPageResponse.Results<T>();
        results.setItems(dataList);
        results.setTotalCount(totalCount);
        response.setResult(results);
        return response;
    }

    public static <T> AjaxPageResponse<T> pageResponseError(String errMessage) {
        AjaxPageResponse<T> response = new AjaxPageResponse<T>();
        response.setAuthorizedRequest(true);
        response.setSuccess(false);
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setMsg(errMessage);
        response.setErrorInfo(errorInfo);
        return response;
    }


    public static <T> AjaxPageResponse<T> pageResponseError(BindingResult bindingResult) {
        AjaxPageResponse<T> response = new AjaxPageResponse<T>();
        response.setAuthorizedRequest(true);
        response.setSuccess(false);
        response.setErrorInfo(ErrorInfoUtil.valueOf(bindingResult));
        return response;
    }


    public static String getBatchResultMsg(int totalCount, int successCount) {
        return getBatchResultMsg(totalCount, successCount, null);
    }

    public static String getBatchResultMsg(int totalCount, int successCount, String errItems) {
        String msg;
        if (totalCount == successCount) {
            if (totalCount == 1) {
                msg = "操作成功";
            } else {
                msg = "操作成功，成功个数：" + successCount;
            }

        } else {
            if (totalCount == 1) {
                msg = "操作失败";
            } else if (successCount == 0) {
                msg = "操作失败，成功个数：0 失败个数：" + (totalCount - successCount);
            } else {
                msg = "操作部分成功，成功个数：" + successCount + " 失败个数：" + (totalCount - successCount);
            }
        }

        if (errItems != null && errItems.length() > 0) {
            msg += " 失败细节：" + errItems;
        }

        return msg;
    }
}
