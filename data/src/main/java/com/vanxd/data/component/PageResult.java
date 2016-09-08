package com.vanxd.data.component;

import java.io.Serializable;
import java.util.List;

/**
 * @author wyd on 2016/9/8.
 */
public class PageResult<T> extends Pagination implements Serializable{
    private Long total = 0l;
    private Long pageCount = 0l;
    private List<T> result = null;
    private int startPage = 1;
    private int endPage = 1;

    public PageResult() {
    }

    public PageResult(Integer pageNo, Integer pageSize) {
        super(pageNo, pageSize);
    }

    public void compute() {
        this.pageCount = Long.valueOf(this.pageSize.intValue() > 0?Long.valueOf((long)(this.total.intValue() / this.pageSize.intValue() + (this.total.intValue() % this.pageSize.intValue() == 0?0:1))).longValue():(long)(this.total.longValue() > 0L?1:0));
        this.startPage = this.pageNo.intValue() > 3?this.pageNo.intValue() - 3:1;
        this.endPage = (int)(this.pageCount.longValue() > (long)(this.startPage + 9)?(long)(this.startPage + 9):this.pageCount.longValue());
    }

    public Long getTotal() {
        return this.total;
    }

    public void setTotal(Long total) {
        this.total = total;
        this.compute();
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public int getStartPage() {
        return this.startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return this.endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    public Long getPageCount() {
        return this.pageCount;
    }
}
