package com.vanxd.data.component;

/**
 * Created by wyd on 2016/8/25.
 */
public class Pagination {
    protected Integer pageSize;
    protected Integer pageNo;

    public Pagination() {
        this.pageSize = Integer.valueOf(10);
        this.pageNo = Integer.valueOf(1);
    }

    public Pagination(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(Integer pageNo) {
        if(pageNo == null) {
            this.pageNo = Integer.valueOf(1);
        } else {
            this.pageNo = pageNo;
        }

    }

    public int getStart() {
        return this.pageNo.intValue() > 1?(this.pageNo.intValue() - 1) * this.pageSize.intValue():0;
    }
}
