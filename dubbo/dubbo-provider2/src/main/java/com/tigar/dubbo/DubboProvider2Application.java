package com.tigar.dubbo;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DubboComponentScan
public class DubboProvider2Application {

    public static void main(String[] args) {
        SpringApplication.run(DubboProvider2Application.class, args);
    }

}