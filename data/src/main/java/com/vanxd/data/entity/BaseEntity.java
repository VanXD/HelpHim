package com.vanxd.data.entity;

import java.util.Date;

/**
 * @author wyd on 2016/9/9.
 */
public class BaseEntity {
    /** 主键 */
    protected String id;
    /**
     * 状态
     * @see com.vanxd.data.dict.StatusEnum
     *  */
    protected Integer status;
    /** 创建时间 */
    protected Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

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
