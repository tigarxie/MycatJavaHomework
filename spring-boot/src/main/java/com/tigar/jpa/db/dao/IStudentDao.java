package com.tigar.jpa.db.dao;

import com.tigar.jpa.db.entity.StudentEntity;
import com.tigar.jpa.db.entityExt.StudentExt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * 根据命名自动生成SQL：https://zhuanlan.zhihu.com/p/25000309
 *
 * @author tigar
 * @date 2019/2/19.
 */
public interface IStudentDao extends JpaRepository<StudentEntity, Long> {
    @Query(value = "SELECT * FROM student WHERE name LIKE %:name%", nativeQuery = true)
    public StudentEntity findByStuName(@Param("name") String name);

    /**
     * 自动分页：https://zhuanlan.zhihu.com/p/25000309
     * 语法和写法：https://blog.csdn.net/qq_36144258/article/details/80298354   返回列表有坑，试了只能使用以下方式输出
     *
     * @param keyword
     * @param pageable
     * @return
     */
    @Query(value = "SELECT st.id AS id, st.name AS name, sc.name AS schoolName FROM student st LEFT JOIN school sc " +
            "ON sc.id = st.school_id WHERE st.name LIKE %:keyword% OR sc.name LIKE %:keyword%",
            countQuery = "SELECT COUNT(*) FROM student st LEFT JOIN school sc " +
                    "ON sc.id = st.school_id WHERE st.name LIKE %:keyword% OR sc.name LIKE %:keyword%",
            nativeQuery = true)
    public Page<Map<String, Object>> findByKey(@Param("keyword") String keyword, Pageable pageable);

    /**
     * 会自动生成Like SQL
     * https://zhuanlan.zhihu.com/p/25000309
     *
     * @param keyword
     * @return
     */
    public List<StudentEntity> findByNameLike(String keyword);
}
