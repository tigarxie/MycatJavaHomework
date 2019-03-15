package com.tigar.elasticsearch;

import com.tigar.elasticsearch.models.Student;
import com.tigar.common.response.AjaxPageResponse;
import com.tigar.common.response.AjaxResponse;
import com.tigar.common.utils.AjaxUtil;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.GroupEntry;
import org.springframework.data.solr.core.query.result.GroupPage;
import org.springframework.data.solr.core.query.result.GroupResult;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * https://blog.csdn.net/mj86534210/article/details/79910909
 *
 * @author tigar
 * @date 2019/2/15.
 */
@RestController
@RequestMapping("/esTemplate")
public class ESTemplateController {

    final private String indexName = "person";
    final private String type = "student";

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;


    @GetMapping("add")
    public AjaxResponse<String> add(@RequestParam String name) throws Exception {
        Student stu = new Student();
        stu.setId(UUID.randomUUID().toString());
        stu.setName(name);
        stu.setDescription("一个灰常牛逼的军事家");
        IndexQuery indexQuery = new IndexQueryBuilder()
//                .withIndexName(indexName).withType(type)  //实体里面已经定义了，所以这里可以不用
                .withId(stu.getId()).withObject(stu).build();
        elasticsearchTemplate.index(indexQuery);
        return AjaxUtil.responseSuccess("成功");
    }

    @GetMapping("deleteById")
    public AjaxResponse<String> deleteById(@RequestParam String id) throws Exception {
        elasticsearchTemplate.delete(Student.class, id);
//        elasticsearchTemplate.delete(indexName,type, id);
        return AjaxUtil.responseSuccess("成功");
    }


    @GetMapping("deleteAll")
    public AjaxResponse<String> deleteAll() {
        DeleteQuery deleteQuery = new DeleteQuery();
        deleteQuery.setIndex(indexName);
        deleteQuery.setQuery(matchAllQuery());
        elasticsearchTemplate.delete(deleteQuery);
        return AjaxUtil.responseSuccess("成功");
    }

    @GetMapping("list")
    public AjaxPageResponse<Student> list(@RequestParam int pageSize, @RequestParam int pageIndex) throws Exception {
        // 分页参数
        Pageable pageable = new QPageRequest(pageIndex, pageSize);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withPageable(pageable).withQuery(matchAllQuery()).build();
        Page<Student> page = elasticsearchTemplate.queryForPage(searchQuery, Student.class);
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
                .withQuery(multiMatchQuery(keyword, "id", "name")).withSort(sortBuilder).build();   // QueryBuilders 工具类里有很多匹配方法

        Page<Student> page = elasticsearchTemplate.queryForPage(searchQuery, Student.class);
        return AjaxUtil.pageResponseSuccess(page.getContent(), page.getTotalPages());
    }
}
