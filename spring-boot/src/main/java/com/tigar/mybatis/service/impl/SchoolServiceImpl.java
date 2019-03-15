package com.tigar.mybatis.service.impl;

import com.tigar.mybatis.po.School;
import com.tigar.mybatis.mapper.SchoolMapper;
import com.tigar.mybatis.service.ISchoolService;
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
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements ISchoolService {

}
