package com.tigar.service;

import com.tigar.dto.UserDTO;
import org.springframework.stereotype.Service;

/**
 * @author tigar
 * @date 2019/3/9.
 */
@Service
public class UserServiceImpl implements IUserService{
    @Override
    public UserDTO getByName(String name) {
        UserDTO user = new UserDTO();
        user.setId(1L);
        user.setName(name);
        user.setSchoolId(1L);
        return user;
    }
}
