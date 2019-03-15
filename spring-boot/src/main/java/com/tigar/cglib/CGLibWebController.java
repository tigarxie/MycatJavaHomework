package com.tigar.cglib;

import com.tigar.common.response.AjaxResponse;
import com.tigar.common.utils.AjaxUtil;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

/**
 * spring开启代理使用CGLib：https://blog.csdn.net/kkgbn/article/details/76914349
 * https://blog.csdn.net/h2453532874/article/details/83022840
 * 原理及使用：https://www.cnblogs.com/yangming1996/p/9270179.html
 *
 * @author tigar
 * @date 2019/3/4.
 */
@RestController
@RequestMapping("/cglib")
public class CGLibWebController {
    @GetMapping("check")
    public AjaxResponse<String> check() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CGLibService.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("前");
                Object rs = method.invoke(o,objects);
                System.out.println("后");
                return rs;
            }
        });
        CGLibService cs = (CGLibService) enhancer.create();
        cs.test();
        return AjaxUtil.responseSuccess("ok");
    }
}
