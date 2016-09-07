package com.vanxd.data.component;

import java.util.List;

/**
 * Created by wyd on 2016/8/25.
 */
public class Pagination {
    private Integer pageSize = 10;
    private Integer pageNo = 1;

    public Pagination() {
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
