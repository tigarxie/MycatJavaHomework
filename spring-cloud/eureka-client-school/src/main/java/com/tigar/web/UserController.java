package com.tigar.web;

import com.tigar.outapi.user.dto.UserDTO;
import com.tigar.outapi.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tigar
 * @date 2019/3/12.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping("getByName")
    public UserDTO getByName(@RequestParam String name){
        return userService.getByName(name);
    }
}
