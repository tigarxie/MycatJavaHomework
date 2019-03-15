package com.tigar.outapi.user.dto;

/**
 * @author tigar
 * @date 2019/3/9.
 */
public class UserDTO {
    private Long id;
    private String name;
    private Long schoolId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }
}
