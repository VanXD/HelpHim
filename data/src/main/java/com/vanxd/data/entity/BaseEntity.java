package com.vanxd.data.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * 每个实体都该有的属性
 *
 * Created by wyd on 2016/8/25.
 */
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @Column(length = 32)
    private String id;
    @Column(nullable = false)
    private Integer status;
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
