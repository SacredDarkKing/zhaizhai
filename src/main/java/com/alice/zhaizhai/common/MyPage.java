package com.alice.zhaizhai.common;

import com.github.pagehelper.Page;

import java.util.List;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月19日 15:33
 */
public class MyPage {
    private List records;//数据记录
    private long total;//总共的数量
    private int size;//页面大小
    private int current;//当前页码

    public MyPage(Page page) {
        this.records = page.getResult();
        this.total = page.getTotal();
        this.size = page.getPageSize();
        this.current = page.getPageNum();
    }

    @Override
    public String toString() {
        return "MyPage{" +
                "records=" + records +
                ", total=" + total +
                ", size=" + size +
                ", current=" + current +
                '}';
    }

    public List getRecords() {
        return records;
    }

    public void setRecords(List records) {
        this.records = records;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
}
