package com.tigar.outapi.school.service;


import com.tigar.outapi.school.dto.SchoolDTO;

/**
 * @author tigar
 * @date 2019/3/12.
 */
public interface ISchoolService {
    SchoolDTO getById(Long id);
}
