package com.tigar.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

/**
 * 注解切面：https://www.cnblogs.com/onlymate/p/9630788.html
 * @author tigar
 * @date 2019/3/4.
 */
@Aspect
public class MyAopConfig {
    @AfterReturning(returning = "rs", pointcut = "@annotation(com.tigar.aop.MyPointcut)")
    public void AfterReturning(Object rs) {
        System.out.println("Aspect 注解切入 返回结果：" + JSON.toJSONString(rs));
    }
}
