package com.tigar.jpa.db.dao;

import com.tigar.jpa.db.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 根据命名自动生成SQL：https://zhuanlan.zhihu.com/p/25000309
 *
 * @author tigar
 * @date 2019/2/19.
 */
public interface ISchoolDao extends JpaRepository<SchoolEntity, Long> {
}
