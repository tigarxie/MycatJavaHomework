package com.tigar.solr;

import com.tigar.common.response.AjaxPageResponse;
import com.tigar.common.response.AjaxResponse;
import com.tigar.common.utils.AjaxUtil;
import com.tigar.solr.models.Person;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 使用：https://www.cnblogs.com/elvinle/p/8149256.html
 * 导入数据库：https://blog.csdn.net/weixin_39082031/article/details/79074078
 * @author tigar
 * @date 2019/1/26.
 */
@RestController
@RequestMapping("/solrClient")
public class SolrClientController {

    final private String collectName = "collection1";

    @Autowired
    private SolrClient client;

    @GetMapping("add")
    public AjaxResponse<String> add(@RequestParam String name) throws Exception {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", UUID.randomUUID().toString());
        document.addField("name", name);
        document.addField("description", "一个灰常牛逼的军事家");
        client.add(collectName, document);
        client.commit(collectName);     // 务必要加上
        return AjaxUtil.responseSuccess("成功");
    }

    @GetMapping("deleteById")
    public AjaxResponse<String> deleteById(@RequestParam String id) throws Exception {
        client.deleteById(collectName, id);
        client.commit(collectName);         // 务必要加上
        return AjaxUtil.responseSuccess("成功");
    }

    @GetMapping("query")
    public AjaxPageResponse<Person> query(@RequestParam String id) throws Exception {
        SolrQuery query = new SolrQuery();

        //下面设置solr查询参数
        //query.set("q", "*:*");// 参数q  查询所有
        query.set("q", "周星驰");//相关查询，比如某条数据某个字段含有周、星、驰三个字  将会查询出来 ，这个作用适用于联想查询

        //参数fq, 给query增加过滤查询条件
        query.addFilterQuery("id:[0 TO 9]");//id为0-4

        //给query增加布尔过滤条件
        //query.addFilterQuery("description:演员");  //description字段中含有“演员”两字的数据

        //参数df,给query设置默认搜索域
        query.set("df", "name");

        //参数sort,设置返回结果的排序规则
        query.setSort("id", SolrQuery.ORDER.desc);

        //设置分页参数
        query.setStart(0);
        query.setRows(10);//每一页多少值

        //参数hl,设置高亮
        query.setHighlight(true);
        //设置高亮的字段
        query.addHighlightField("name");
        //设置高亮的样式
        query.setHighlightSimplePre("<font color='red'>");
        query.setHighlightSimplePost("</font>");

        //获取查询结果
        QueryResponse response = client.query(collectName, query);
        //两种结果获取：得到文档集合或者实体对象

        //查询得到文档的集合
        SolrDocumentList solrDocumentList = response.getResults();

        return AjaxUtil.pageResponseSuccess(solrDocumentList.stream().map(m -> {
            Person person = new Person();
            person.setId(m.get("id").toString());
            person.setName(m.get("name").toString());
            person.setDescription(m.get("description").toString());
            return person;
        }).collect(Collectors.toList()), (int)solrDocumentList.getNumFound());
    }
}
