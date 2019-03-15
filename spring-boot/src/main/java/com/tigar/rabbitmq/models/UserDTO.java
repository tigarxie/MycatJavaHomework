package com.tigar.rabbitmq.models;

/**
 * @author tigar
 * @date 2019/1/10.
 */
public class UserDTO {
    private String name;
    private Integer age;
    private  String className;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "rabbitmq私有_UserDTO{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", className='" + className + '\'' +
                '}';
    }
}
