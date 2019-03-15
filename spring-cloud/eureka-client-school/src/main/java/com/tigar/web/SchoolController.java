package com.tigar.web;

import com.tigar.dto.SchoolDTO;
import com.tigar.service.ISchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tigar
 * @date 2019/3/12.
 */
@RestController
public class SchoolController {

    @Autowired
    ISchoolService schoolService;

    @GetMapping("getById")
    public SchoolDTO getById(@RequestParam long id){
        return schoolService.getById(id);
    }
}
