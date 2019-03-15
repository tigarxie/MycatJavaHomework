package com.tigar.dubbo.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tigar.dubbo.api.IHelloService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tigar
 * @date 2019/1/10.
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Reference
    private IHelloService helloService;


    @RequestMapping(value = "get", method = RequestMethod.GET)
    public String get1(@RequestParam String str) {
        String res = helloService.Say(str);
        return res;
    }

}