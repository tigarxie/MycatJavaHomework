package com.tigar.elasticsearch;

import com.tigar.elasticsearch.models.Student;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author tigar
 * @date 2019/2/15.
 */
public interface IStudentEsService extends ElasticsearchRepository<Student,String> {
}
