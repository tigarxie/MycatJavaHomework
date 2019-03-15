package com.tigar.cglib;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 学习：https://www.cnblogs.com/xrq730/p/6661692.html
 * @author tigar
 * @date 2019/3/4.
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)    // 强制开启CGLib
public class CGLibConfig {
}
