package com.tigar.jpa.db.service.impl;

import com.tigar.jpa.db.dao.ISchoolDao;
import com.tigar.jpa.db.dao.IStudentDao;
import com.tigar.jpa.db.entity.SchoolEntity;
import com.tigar.jpa.db.entity.StudentEntity;
import com.tigar.jpa.db.service.IStudentJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author tigar
 * @date 2019/2/19.
 */
@Component
public class StudentJpaServiceImpl implements IStudentJpaService {

    @Autowired
    private ISchoolDao schoolDao;

    @Autowired
    private IStudentDao studentDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void transTests() {
        SchoolEntity ent = new SchoolEntity();
        ent.setName(UUID.randomUUID().toString());
        schoolDao.save(ent);

        // 以下会失败
        StudentEntity stu = new StudentEntity();
        studentDao.save(stu);
    }
}
