package com.tigar.mybatis.service.impl;

import com.tigar.mybatis.po.Student;
import com.tigar.mybatis.mapper.StudentMapper;
import com.tigar.mybatis.service.IStudentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tigar
 * @since 2019-02-20
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
