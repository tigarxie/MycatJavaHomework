package com.tigar.outapi.user.service;


import com.tigar.outapi.user.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 基础：
 * 熔断：https://blog.csdn.net/forezp/article/details/81040990
 * @author tigar
 * @date 2019/3/9.
 */
@FeignClient(value = "eureka-user-service", fallback = UserServieFallBack.class)
public interface IUserService {
    @RequestMapping("/user/getByName")
    UserDTO getByName(@RequestParam(value = "name") String name);
}
