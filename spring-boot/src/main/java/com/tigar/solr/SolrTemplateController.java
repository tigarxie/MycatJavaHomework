package com.tigar.solr;

import com.tigar.common.response.AjaxPageResponse;
import com.tigar.common.response.AjaxResponse;
import com.tigar.common.utils.AjaxUtil;
import com.tigar.solr.models.Person;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
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
import java.util.stream.Collectors;

/**
 * 使用：https://segmentfault.com/a/1190000000412105
 * 场景：http://www.cnblogs.com/wang-meng/p/5819792.html
 * 常见错误：https://www.2cto.com/net/201806/757810.html
 *
 * @author tigar
 * @date 2019/1/26.
 */
@RestController
@RequestMapping("/solrTemplate")
public class SolrTemplateController {

    final private String collectName = "collection1";

    @Autowired
    private SolrTemplate solrTemplate;

    @GetMapping("add")
    public AjaxResponse<String> add(@RequestParam String name) throws Exception {
        Person person = new Person();
        person.setId(UUID.randomUUID().toString());
        person.setName(name);
        person.setDescription("一个灰常牛逼的军事家");
        solrTemplate.saveBean(collectName, person);
        solrTemplate.commit(collectName);
        return AjaxUtil.responseSuccess("成功");
    }

    @GetMapping("deleteById")
    public AjaxResponse<String> deleteById(@RequestParam String id) throws Exception {
        solrTemplate.deleteByIds(collectName, id);
        solrTemplate.commit(collectName);
        return AjaxUtil.responseSuccess("成功");
    }


    @GetMapping("deleteAll")
    public AjaxResponse<String> deleteAll() {
        Query query = new SimpleQuery("*:*");
        solrTemplate.delete(collectName, query);
        solrTemplate.commit(collectName);
        return AjaxUtil.responseSuccess("成功");
    }

    @GetMapping("list")
    public AjaxPageResponse<Person> list(@RequestParam int pageSize, @RequestParam int pageIndex) throws Exception {
        Query query = new SimpleQuery("*:*");
        query.setOffset((pageIndex - 1) * pageSize * 1L);//开始索引（默认0）start:(page-1)*rows
        query.setRows(pageSize);//每页记录数(默认10)//rows:rows
        ScoredPage<Person> res = solrTemplate.queryForPage(collectName, query, Person.class);
        return AjaxUtil.pageResponseSuccess(res.getContent(), Integer.valueOf(String.valueOf(res.getTotalElements())));
    }

    @GetMapping("group")
    public AjaxResponse<Map<String, Integer>> group(@RequestParam String groupName) throws Exception {
        Query query = new SimpleQuery("*:*");
        GroupOptions options = new GroupOptions().addGroupByField(groupName);
        query.setGroupOptions(options);
        // 执行查询,得到所有的分组数据
        GroupPage<Person> groupPage = solrTemplate.queryForGroupPage(collectName, query, Person.class);
        // 有可能指定了多个分组,所以根据指定的分组来获取分组结果
        GroupResult<Person> groupResult = groupPage.getGroupResult(groupName);
        // 获取分组入口对象
        Page<GroupEntry<Person>> groupEntries = groupResult.getGroupEntries();
        Map<String, Integer> map = new HashMap<>();
        for (GroupEntry<Person> entry : groupEntries) {
            map.put(entry.getGroupValue(), entry.getResult().getTotalPages());
        }
        return AjaxUtil.responseSuccess(map);
    }

    @GetMapping("query")
    public AjaxPageResponse<Person> query(@RequestParam String keyword, @RequestParam(required = false) String filterword,
                                          @RequestParam(required = false) String sortField, @RequestParam(required = false) Boolean isASC,
                                          @RequestParam int pageSize, @RequestParam int pageIndex) throws Exception {
        //1、设置高亮选项
        //根据关键字查询,构建高亮显示查询对象
        HighlightQuery query = new SimpleHighlightQuery();
        //addField(String fieldName) 在哪一个域上高亮显示,此处设置在title高亮显示
        HighlightOptions highlightOptions = new HighlightOptions().addField("name");
        //设置前缀
        highlightOptions.setSimplePrefix("<em style='color:red'>");
        //设置后缀
        highlightOptions.setSimplePostfix("</em>");
        //为查询对象设置高亮选项
        query.setHighlightOptions(highlightOptions);

        //2、构建查询条件 关键字查询
        Criteria condi = new Criteria("id").contains(keyword)
                .or(new Criteria("name").is(keyword));
        query.addCriteria(condi);

        //3、分类过滤查询
        if (filterword != null && !filterword.equals("")) {
            FilterQuery idQuery = new SimpleFilterQuery();
            Criteria idfilter = new Criteria("id").is(filterword);
            idQuery.addCriteria(idfilter);
            query.addFilterQuery(idQuery);

            FilterQuery nameQuery = new SimpleFilterQuery();
            Criteria namefilter = new Criteria("name").is(filterword);
            idQuery.addCriteria(namefilter);
            query.addFilterQuery(nameQuery);
        }

        //4、分页
        query.setOffset((pageIndex - 1) * pageSize * 1L);//开始索引（默认0）start:(page-1)*rows
        query.setRows(pageSize);//每页记录数(默认10)//rows:rows

        //5、排序
        if (sortField != null && !sortField.equals("")) {
            if (isASC) {
                query.addSort(new Sort(Sort.Direction.ASC, sortField));
            } else {
                query.addSort(new Sort(Sort.Direction.DESC, sortField));
            }
        }

        ScoredPage<Person> res = solrTemplate.queryForPage(collectName, query, Person.class);
        return AjaxUtil.pageResponseSuccess(res.getContent(), Integer.valueOf(String.valueOf(res.getTotalElements())));
    }
}
