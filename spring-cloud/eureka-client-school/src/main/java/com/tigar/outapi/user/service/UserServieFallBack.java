package com.tigar.outapi.user.service;

import com.tigar.outapi.user.dto.UserDTO;
import org.springframework.stereotype.Component;

/**
 * 熔断：https://blog.csdn.net/forezp/article/details/81040990
 * @author tigar
 * @date 2019/3/12.
 */
@Component
public class UserServieFallBack implements IUserService{
    @Override
    public UserDTO getByName(String name) {
        return new UserDTO();
    }
}
