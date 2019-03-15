package com.tigar.jpa.db.entityExt;

import javax.persistence.Column;

/**
 * @author tigar
 * @date 2019/2/19.
 */
public class StudentExt {
    private long id;
    private String name;
    private String schoolName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
