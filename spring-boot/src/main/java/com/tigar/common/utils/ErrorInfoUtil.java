package com.tigar.common.utils;

import com.tigar.common.response.ErrorInfo;
import com.tigar.common.response.ValidationErrorInfo;
import org.springframework.validation.BindingResult;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.stream.Collectors;

/**
 * @author tigar
 * @date 2019/2/1.
 */
public class ErrorInfoUtil {
    public static ErrorInfo valueOf(BindingResult bindingResult){
        ErrorInfo errorInfo = new ErrorInfo();
        ValidationErrorInfo validationErrorInfo =new ValidationErrorInfo();
        validationErrorInfo.setMessage(bindingResult.getFieldError().getDefaultMessage());
        validationErrorInfo.setMembers(bindingResult.getFieldErrors()
                .stream()
                .map(fe->fe.getField())
                .collect(Collectors.toList()));
        errorInfo.setValidationErrorInfo(validationErrorInfo);

        return errorInfo;
    }

    public static ErrorInfo ErrorMsg(String error){
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setMsg(error);
        return errorInfo;
    }

    public static String printStackTraceToString(Throwable t){
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }
}
