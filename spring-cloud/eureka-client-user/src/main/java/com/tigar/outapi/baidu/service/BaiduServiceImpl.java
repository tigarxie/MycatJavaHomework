package com.tigar.outapi.baidu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * @author tigar
 * @date 2019/3/12.
 */
public class BaiduServiceImpl implements IBaiduService{
    @Autowired
    RestTemplate restTemplate;

    @Override
    public String get() {
        return restTemplate.getForObject("http://", String.class);
    }
}
