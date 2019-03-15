package com.tigar.outapi.school.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tigar.outapi.school.dto.SchoolDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Ribbon：https://blog.csdn.net/forezp/article/details/81040946
 * 熔断：https://blog.csdn.net/forezp/article/details/81040990
 * @author tigar
 * @date 2019/3/12.
 */
@Service
public class SchoolServiceImpl implements ISchoolService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    @HystrixCommand(fallbackMethod = "getByIdFallBack")
    public SchoolDTO getById(Long id) {
        return restTemplate.getForObject(String.format("http://eureka-school-service/getById?id=%d", id), SchoolDTO.class);
    }

    public SchoolDTO getByIdFallBack(Long id) {
        SchoolDTO school = new SchoolDTO();
        school.setId(id);
        return school;
    }
}
