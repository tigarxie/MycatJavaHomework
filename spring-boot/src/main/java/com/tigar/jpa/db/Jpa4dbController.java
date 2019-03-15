package com.tigar.jpa.db;

import com.tigar.common.response.AjaxPageResponse;
import com.tigar.common.response.AjaxResponse;
import com.tigar.common.utils.AjaxUtil;
import com.tigar.common.utils.ObjectUtil;
import com.tigar.common.utils.StringUtil;
import com.tigar.jpa.db.entity.StudentEntity;
import com.tigar.jpa.db.entityExt.StudentExt;
import com.tigar.jpa.db.dao.IStudentDao;
import com.tigar.jpa.db.service.IStudentJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 代码生成：https://blog.csdn.net/qq_21768483/article/details/78293396
 * Eclipse生成：https://blog.csdn.net/xiang__liu/article/details/80805967
 *
 * @author tigar
 * @date 2019/2/18.
 */
@RestController
@RequestMapping("/jpa4db")
public class Jpa4dbController {

    @Autowired
    private IStudentJpaService studentService;

    @Autowired
    private IStudentDao studentDao;

    @GetMapping("add")
    public AjaxResponse<String> add(@RequestParam int age) throws Exception {
        StudentEntity ent = new StudentEntity();
        ent.setName(UUID.randomUUID().toString());
        ent.setAge(age);
        studentDao.save(ent);
        return AjaxUtil.responseSuccess("成功");
    }

    @GetMapping("deleteById")
    public AjaxResponse<String> deleteById(@RequestParam long id) throws Exception {
        studentDao.deleteById(id);
        return AjaxUtil.responseSuccess("成功");
    }


    @GetMapping("deleteAll")
    public AjaxResponse<String> deleteAll() {
        studentDao.deleteAll();
        return AjaxUtil.responseSuccess("成功");
    }

    @GetMapping("list")
    public AjaxPageResponse<StudentEntity> list(@RequestParam int pageSize, @RequestParam int pageIndex) throws Exception {
        // 分页参数
        Pageable pageable = new PageRequest(pageIndex, pageSize, new Sort(Sort.Direction.DESC, "id"));
        Page<StudentEntity> page = studentDao.findAll(pageable);
        return AjaxUtil.pageResponseSuccess(page.getContent(), page.getTotalPages());
    }

    @GetMapping("query")
    public AjaxPageResponse<StudentExt> query(@RequestParam String keyword,
                                              @RequestParam(required = false) String sortField, @RequestParam(required = false) Boolean isASC,
                                              @RequestParam int pageSize, @RequestParam int pageIndex) throws Exception {
        List<StudentExt> list = null;

        // 语法和写法：https://blog.csdn.net/qq_36144258/article/details/80298354   返回列表有坑，试了只能使用以下方式输出
        Sort sort = new Sort(isASC != null && isASC == true ? Sort.Direction.ASC : Sort.Direction.DESC, StringUtil.isNullOrEmpty(sortField) ? "id" : sortField);
        Pageable pageable = new PageRequest(pageIndex, pageSize, sort);
        Page<Map<String, Object>> rs = studentDao.findByKey(keyword, pageable);
        if (rs.getTotalPages() > 0) {
            list = rs.stream().map(m -> {
                StudentExt item = new StudentExt();
                item.setId(ObjectUtil.toLong(m.get("id")));
                item.setName(ObjectUtil.toStr(m.get("name")));
                item.setSchoolName(ObjectUtil.toStr(m.get("schoolName")));
                return item;
            }).collect(Collectors.toList());
        }
        return AjaxUtil.pageResponseSuccess(list, rs.getTotalPages());
    }

    @GetMapping("transTest")
    public AjaxResponse<String> transTest() throws Exception {
        try {
            studentService.transTests();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxUtil.responseSuccess("完成");
    }
}
