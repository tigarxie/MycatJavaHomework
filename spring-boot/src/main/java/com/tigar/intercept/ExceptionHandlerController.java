package com.tigar.intercept;

import com.tigar.common.response.AjaxResponse;
import com.tigar.common.response.ErrorInfo;
import com.tigar.common.response.ValidationErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author tigar
 * @date 2019/1/25.
 */
@RestControllerAdvice
@Controller
public class ExceptionHandlerController {
    /**
     * 获取的运行环境
     */
    @Value("${spring.profiles.active}")
    private String profile;

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    /**
     * 将异常信息封装到AjaxResponse,返回给调用者
     *
     * @param e 异常对象
     * @return AjaxResponse
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<AjaxResponse<Void>> exceptionHandler(HttpServletRequest request, Exception e) {
        AjaxResponse<Void> ajaxResponse = AjaxResponse.getInstance();
        ErrorInfo errorInfo = new ErrorInfo();
        ajaxResponse.setSuccess(Boolean.FALSE);
        ajaxResponse.setTargetUrl(request.getRequestURI());
        logger.error("请求 {} 时发生异常 {} {}", request.getRequestURI(), e.getMessage(), e);
        if (e instanceof BindException) {
            List<FieldError> errors = ((BindException) e).getFieldErrors();
            StringBuilder sb = new StringBuilder();
            for (FieldError fieldError : errors) {
                String defaultMessage = fieldError.getDefaultMessage();
                sb.append(defaultMessage);
            }
            errorInfo.setMsg(sb.toString());
            ajaxResponse.setErrorInfo(errorInfo);
            return new ResponseEntity<>(ajaxResponse, HttpStatus.BAD_REQUEST);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            String requestUri = request.getRequestURI();
            String queryType = request.getMethod();
            errorInfo.setMsg(requestUri + " 不支持 " + queryType + " 方式请求");
            ajaxResponse.setErrorInfo(errorInfo);
            return new ResponseEntity<>(ajaxResponse, HttpStatus.METHOD_NOT_ALLOWED);
        } else if (e instanceof IllegalStateException || e instanceof IllegalArgumentException) {
            errorInfo.setMsg(e.getMessage());
            ajaxResponse.setErrorInfo(errorInfo);
            return new ResponseEntity<>(ajaxResponse, HttpStatus.BAD_REQUEST);
        } else if (e instanceof HttpMediaTypeNotSupportedException) {
            errorInfo.setMsg(e.getMessage());
            ajaxResponse.setErrorInfo(errorInfo);
            return new ResponseEntity<>(ajaxResponse, HttpStatus.BAD_REQUEST);
        } else if (e instanceof MethodArgumentNotValidException) {
            exceptionHandler((MethodArgumentNotValidException) e, errorInfo);
            ajaxResponse.setErrorInfo(errorInfo);
            return new ResponseEntity<>(ajaxResponse, HttpStatus.OK);
        } else if (e instanceof ConstraintViolationException) {
            exceptionHandler((ConstraintViolationException) e, errorInfo);
            ajaxResponse.setErrorInfo(errorInfo);
            return new ResponseEntity<>(ajaxResponse, HttpStatus.OK);
        } else {
            // 错误原因
            if (!"dev".equals(profile)) {
                errorInfo.setMsg("Oh~~网络开小差，请检查网络！");
                //测试或者生产环境返回出成功
                return new ResponseEntity<>(ajaxResponse, HttpStatus.OK);
            } else {
                errorInfo.setMsg(e.getMessage());
            }
            ajaxResponse.setErrorInfo(errorInfo);
        }
        return new ResponseEntity<>(ajaxResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        // TODO 根据异常细分返回状态码
    }


    /**
     * 处理绑定参数错误
     *
     * @param e
     * @param erroeInfo
     */
    private void exceptionHandler(MethodArgumentNotValidException e, ErrorInfo erroeInfo) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuffer sb = new StringBuffer();
        List<String> members = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            if (!CollectionUtils.isEmpty(errorList)) {
                for (ObjectError objectError : errorList) {
                    sb.append(objectError.getDefaultMessage()).append(";");
                    Object[] obj = objectError.getArguments();
                    if (1 <= obj.length) {
                        DefaultMessageSourceResolvable obj1 = (DefaultMessageSourceResolvable) obj[0];
                        if (2 <= obj1.getCodes().length) {
                            String field = obj1.getCodes()[1];
                            members.add(field);
                        }
                    }
                }
            }
        }
        erroeInfo.setMsg(sb.toString());
        ValidationErrorInfo vinfo = new ValidationErrorInfo();
        vinfo.setMessage(sb.toString());
        vinfo.setMembers(members);
        erroeInfo.setValidationErrorInfo(vinfo);
    }

    /**
     * 处理Controller校验错误
     *
     * @param e
     * @param erroeInfo
     */
    private void exceptionHandler(ConstraintViolationException e, ErrorInfo erroeInfo) {

        Set<ConstraintViolation<?>> constraintViolationSet = e.getConstraintViolations();
        StringBuffer sb = new StringBuffer();
        List<String> members = new ArrayList<>();
        if (constraintViolationSet != null && constraintViolationSet.size() > 0) {
            sb.append(constraintViolationSet.stream().map(l -> l.getMessage()).collect(Collectors.toList()));
            members.addAll(constraintViolationSet.stream().map(l -> l.getPropertyPath().toString()).collect(Collectors.toList()));
        }
        erroeInfo.setMsg(sb.toString());
        ValidationErrorInfo vinfo = new ValidationErrorInfo();
        vinfo.setMessage(sb.toString());
        vinfo.setMembers(members);
        erroeInfo.setValidationErrorInfo(vinfo);
    }
}

