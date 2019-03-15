package com.tigar.aop;

import com.tigar.common.response.AjaxResponse;
import com.tigar.common.utils.AjaxUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tigar
 * @date 2019/3/4.
 */
@RestController
@RequestMapping("/myPointcutweb")
public class MyPointcutWebController {

    @MyPointcut
    @GetMapping("check")
    public AjaxResponse<String> check() throws Exception {
        return AjaxUtil.responseSuccess("ok");
    }

    @GetMapping("error")
    public AjaxResponse<String> error() throws Exception {
        throw new Exception("s");
    }
}

