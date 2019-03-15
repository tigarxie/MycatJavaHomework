package com.tigar.service;

import com.tigar.dto.SchoolDTO;

/**
 * @author tigar
 * @date 2019/3/12.
 */
public interface ISchoolService {
    SchoolDTO getById(Long id);
}
