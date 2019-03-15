package com.tigar.dubbo.provider1;

import com.alibaba.dubbo.config.annotation.Service;
import com.tigar.dubbo.api.IHelloService;

/**
 * @author tigar
 * @date 2019/1/10.
 */
@Service
public class HelloServiceImpl implements IHelloService {
    @Override
    public String Say(String str) {
        return "provider1:" + str;
    }
}
