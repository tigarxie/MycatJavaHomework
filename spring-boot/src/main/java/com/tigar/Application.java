package com.tigar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.tigar", exclude = {DataSourceAutoConfiguration.class})
public class Application {

	public static void main(String[] args) {
		// 解决ES跟rocketMQ冲突
		// https://blog.csdn.net/q258523454/article/details/82387130
		System.setProperty("es.set.netty.runtime.available.processors", "false");
		SpringApplication.run(Application.class, args);
	}

}

