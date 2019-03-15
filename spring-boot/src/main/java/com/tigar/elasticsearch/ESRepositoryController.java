package com.tigar.elasticsearch;

import com.tigar.common.response.AjaxPageResponse;
import com.tigar.common.response.AjaxResponse;
import com.tigar.common.utils.AjaxUtil;
import com.tigar.elasticsearch.models.Student;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

/**
 * https://blog.csdn.net/larger5/article/details/79777319
 *
 * @author tigar
 * @date 2019/2/16.
 */
@RestController
@RequestMapping("/esRepository")
public class ESRepositoryController {

    @Autowired
    IStudentEsService studentService;

    @GetMapping("add")
    public AjaxResponse<String> add(@RequestParam String name) throws Exception {
        Student stu = new Student();
        stu.setId(UUID.randomUUID().toString());
        stu.setName(name);
        stu.setDescription("一个灰常牛逼的军事家");
        studentService.save(stu);
        return AjaxUtil.responseSuccess("成功");
    }

    @GetMapping("deleteById")
    public AjaxResponse<String> deleteById(@RequestParam String id) throws Exception {
        studentService.deleteById(id);
        return AjaxUtil.responseSuccess("成功");
    }


    @GetMapping("deleteAll")
    public AjaxResponse<String> deleteAll() {
        studentService.deleteAll();
        return AjaxUtil.responseSuccess("成功");
    }

    @GetMapping("list")
    public AjaxPageResponse<Student> list(@RequestParam int pageSize, @RequestParam int pageIndex) throws Exception {
        // 分页参数
        Pageable pageable = new QPageRequest(pageIndex, pageSize);
        Page<Student> page = studentService.findAll(pageable);
        return AjaxUtil.pageResponseSuccess(page.getContent(), page.getTotalPages());
    }

    @GetMapping("query")
    public AjaxPageResponse<Student> query(@RequestParam String keyword, @RequestParam(required = false) String filterword,
                                           @RequestParam(required = false) String sortField, @RequestParam(required = false) Boolean isASC,
                                           @RequestParam int pageSize, @RequestParam int pageIndex) throws Exception {

        SortBuilder sortBuilder = SortBuilders.fieldSort(sortField)   //排序字段
                .order(isASC != null && isASC == true ? SortOrder.ASC : SortOrder.DESC);   //排序方式

        // 分页参数
        Pageable pageable = new QPageRequest(pageIndex, pageSize);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withPageable(pageable)
                .withQuery(multiMatchQuery(keyword, "id", "name")).withSort(sortBuilder).build();
        Page<Student> page = studentService.search(searchQuery);
        return AjaxUtil.pageResponseSuccess(page.getContent(), page.getTotalPages());
    }
}

