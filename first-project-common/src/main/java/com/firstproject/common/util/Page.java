package com.firstproject.common.util;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Description：
 * 分页工具类
 *
 * @author zhouzhongyi1
 * DATE： 2018/10/12 14:05
 */
public class Page<T> implements Serializable {
    private static final long serialVersionUID = 3362596825598239090L;
    private static final int MAX_PAGESIZE = 500;
    /**
     * 总数
     */
    @Setter
    @Getter
    private int totalCount = 0;
    /**
     * 每页展示数量
     */
    @Getter
    private Integer pageSize = 20;

    public void setPageSize(Integer pageSize) {
        if (pageSize == null || pageSize <= 0) {
            this.pageSize = 20;
        } else {
            this.pageSize = Math.min(pageSize, Page.MAX_PAGESIZE);
        }
    }

    /**
     * 当前页码
     */
    private Integer pageNum = 1;

    public void setPageNum(Integer pageNum) {
        if (pageNum == null || pageNum <= 0) {
            this.pageNum = 1;
        } else {
            this.pageNum = pageNum;
        }
    }

    public Integer getPageNum() {
        if (this.totalCount <= 0) {
            return this.pageNum;
        } else {
            return this.pageNum <= this.getPageCount() ? this.pageNum : this.getPageCount();
        }
    }

    /**
     * 分页数据
     */
    @Setter
    @Getter
    private List<T> data = null;

    /**
     * 数据库用偏移量
     *
     * @return
     */
    public int getOffset() {
        int pn = this.getPageNum();
        return pn > 1 ? (pn - 1) * this.pageSize : 0;
    }

    /**
     * 共多少页
     *
     * @return
     */
    public int getPageCount() {
        if (this.totalCount <= 0) {
            return 1;
        } else {
            return (this.totalCount % this.pageSize) == 0 ? this.totalCount / this.pageSize : this.totalCount / this.pageSize + 1;
        }
    }

    public boolean isFirstPage() {
        return this.getPageNum() <= 1;
    }

    public boolean isLastPage() {
        return this.getPageNum() >= this.getPageCount();
    }

    public int getPreviousPage() {
        return this.isFirstPage() ? 1 : this.pageNum - 1;
    }

    public int getNextPage() {
        return this.isLastPage() ? this.pageNum : this.pageNum + 1;
    }

    public Page() {
    }

    public Page(int pageNum, int pageSize) {
        this(pageNum, pageSize, 0);
    }

    public Page(int pageNum, int pageSize, int totalCount) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    public Page(int pageNum, int pageSize, int totalCount, List<T> data) {
        this(pageNum, pageSize, totalCount);
        this.data = data;
    }
}
