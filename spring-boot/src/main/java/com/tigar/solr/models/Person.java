package com.tigar.solr.models;

import org.apache.solr.client.solrj.beans.Field;

/**
 * @author tigar
 * @date 2019/1/26.
 */


public class Person {

    @Field(value="id")
    private String id;

    @Field(value="name")
    private String name;

    @Field(value="desc")
    private String description;

    private Integer age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
