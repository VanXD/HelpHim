package com.vanxd.data.entity;

/**
 * @author wyd on 2016/9/9.
 */
public class BaseEntity {
    protected String id;

    protected Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
