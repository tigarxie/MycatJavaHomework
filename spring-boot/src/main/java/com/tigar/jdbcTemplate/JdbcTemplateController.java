package com.tigar.jdbcTemplate;

import com.tigar.common.response.AjaxPageResponse;
import com.tigar.common.response.AjaxResponse;
import com.tigar.common.utils.AjaxUtil;
import com.tigar.jpa.db.entity.StudentEntity;
import com.tigar.jpa.db.entityExt.StudentExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 常用方法：https://www.cnblogs.com/wanggd/p/3140506.html
 * @author tigar
 * @date 2019/2/20.
 */
@RestController
@RequestMapping("/jdbcTemplate")
public class JdbcTemplateController {

    @Autowired
    @Qualifier("tigarJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @GetMapping("add")
    public AjaxResponse<String> add(@RequestParam int age) throws Exception {
        jdbcTemplate.update("INSERT INTO student (name, age, school_id) VALUE (?, ?, ?)", UUID.randomUUID().toString(), age, 1);
        return AjaxUtil.responseSuccess("成功");
    }

    @GetMapping("deleteById")
    public AjaxResponse<String> deleteById(@RequestParam long id) throws Exception {
        jdbcTemplate.update("DELETE FROM student WHERE id = ?", id);
        return AjaxUtil.responseSuccess("成功");
    }


    @GetMapping("deleteAll")
    public AjaxResponse<String> deleteAll() {
        jdbcTemplate.update("DELETE FROM student");
        return AjaxUtil.responseSuccess("成功");
    }

    /**
     * 注意避开神坑：https://www.cnblogs.com/davidwang456/p/4511013.html
     *
     * @param pageSize
     * @param pageIndex
     * @return
     * @throws Exception
     */
    @GetMapping("list")
    public AjaxPageResponse<StudentEntity> list(@RequestParam int pageSize, @RequestParam int pageIndex) throws Exception {
        Integer totalCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM student", Integer.class);
        List<StudentEntity> list = null;
        if (totalCount != null && totalCount > 0) {
            // List<T> queryForList(String sql, Class<T> elementType) 有坑
            // 详情见：https://www.cnblogs.com/davidwang456/p/4511013.html
            List<Map<String, Object>> rs =
                    jdbcTemplate.queryForList(String.format("SELECT id,name,age,school_id FROM student LIMIT %d, %d",
                            (pageIndex - 1) * pageSize, pageSize * pageIndex));
            list = rs.stream().map(m -> {
                StudentEntity item = new StudentEntity();
                item.setId(Long.valueOf(m.getOrDefault("id", 0).toString()));
                item.setName(m.getOrDefault("name", "").toString());
                item.setAge(Integer.valueOf(m.getOrDefault("age", 0).toString()));
                item.setSchoolId(Long.valueOf(m.getOrDefault("school_id", 0).toString()));
                return item;
            }).collect(Collectors.toList());
        }
        return AjaxUtil.pageResponseSuccess(list, (totalCount + pageSize - 1) / pageSize);
    }

    /**
     * 批量执行加速：https://blog.csdn.net/shushugood/article/details/81005718，连接字串加入rewriteBatchedStatements=true
     *
     * @return
     * @throws Exception
     */
    @GetMapping("batchUpdate")
    public AjaxResponse<String> batchUpdate() throws Exception {
        jdbcTemplate.batchUpdate("INSERT INTO student (name, age, school_id) VALUE (?, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt, int i) throws SQLException {
                pstmt.setString(1, UUID.randomUUID().toString());
                pstmt.setInt(2, 1);
                pstmt.setInt(3, 1);
            }

            @Override
            public int getBatchSize() {
                return 10000;
            }
        });
        return AjaxUtil.responseSuccess("完成");
    }
}
