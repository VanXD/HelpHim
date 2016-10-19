package com.vanxd.data.component;

import java.io.Serializable;
import java.util.List;

/**
 * @author wyd on 2016/9/8.
 */
public class PageResult<T> extends Pagination implements Serializable{
    /** 数据总数 */
    private Long records = 0l;
    /** 总页数 */
    private Long total = 0l;
    /** 数据 */
    private List<T> rows = null;
    private int startPage = 1;
    private int endPage = 1;

    public PageResult() {
    }

    public PageResult(Integer pageNo, Integer pageSize) {
        super(pageNo, pageSize);
    }

    public void compute() {
        this.total = Long.valueOf(this.pageSize.intValue() > 0?Long.valueOf((long)(this.records.intValue() / this.pageSize.intValue() + (this.records.intValue() % this.pageSize.intValue() == 0?0:1))).longValue():(long)(this.records.longValue() > 0L?1:0));
        this.startPage = this.page.intValue() > 3?this.page.intValue() - 3:1;
        this.endPage = (int)(this.total.longValue() > (long)(this.startPage + 9)?(long)(this.startPage + 9):this.total.longValue());
    }

    public Long getRecords() {
        return this.records;
    }

    public void setRecords(Long records) {
        this.records = records;
        this.compute();
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
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

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getTotal() {
        return this.total;
    }
}
