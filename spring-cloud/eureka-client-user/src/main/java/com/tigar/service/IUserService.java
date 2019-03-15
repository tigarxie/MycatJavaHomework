package com.tigar.service;

import com.tigar.dto.UserDTO;

/**
 * @author tigar
 * @date 2019/3/9.
 */
public interface IUserService {
    UserDTO getByName(String name);
}
