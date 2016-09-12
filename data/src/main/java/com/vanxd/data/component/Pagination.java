package com.vanxd.data.component;

/**
 * Created by wyd on 2016/8/25.
 */
public class Pagination {
    protected Integer pageSize = 10;
    protected Integer page = 1;

    public Pagination() {
    }

    public Pagination(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        if(page == null) {
            this.page = Integer.valueOf(1);
        } else {
            this.page = page;
        }

    }

    public int getStart() {
        return this.page.intValue() > 1?(this.page.intValue() - 1) * this.pageSize.intValue():0;
    }

}
