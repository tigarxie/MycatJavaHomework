package com.tigar.service;

import com.tigar.dto.SchoolDTO;
import org.springframework.stereotype.Service;

/**
 * @author tigar
 * @date 2019/3/12.
 */
@Service
public class SchoolServiceImpl implements ISchoolService {
    @Override
    public SchoolDTO getById(Long id) {
        SchoolDTO school = new SchoolDTO();
        school.setId(id);
        school.setName(String.format("南方学校%d", id));
        return school;
    }
}
