package com.tigar.aop;

/**
 * AOP的几种方式：http://www.360doc.com/content/16/0422/23/20874412_552985059.shtml
 * 写多个：https://blog.csdn.net/super_scan/article/details/39005071
 * @author tigar
 * @date 2019/3/4.
 */

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AopConfig {

    @Pointcut("execution(public * com.tigar.aop.AopWebController.*(..))")
    public void pointcutSet() {
        System.out.println("Aspect 定义");
    }

    @Before("pointcutSet()")
    public void before(JoinPoint joinPoint) throws Throwable {
        System.out.println("Aspect 执行开始");
    }

    @After("pointcutSet()")
    public void after(JoinPoint joinPoint) throws Throwable {
        System.out.println("Aspect 执行结束");
    }

    @AfterReturning(returning = "rs", pointcut = "execution(public * com.tigar.aop.AopWebController.error()) || execution(public * com.tigar.aop.AopWebController.check())")
    public void afterReturning(Object rs) {
        System.out.println("Aspect 返回结果：" + JSON.toJSONString(rs));
    }

    @AfterThrowing(throwing = "ex", pointcut = "pointcutSet()")
    public void afterReturning(Throwable ex) {
        System.out.println("Aspect 有异常");
    }
}
