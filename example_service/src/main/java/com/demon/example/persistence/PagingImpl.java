package com.demon.example.persistence;

public class PagingImpl implements Paging {

    private Integer offset;
    private Integer rows;
    
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    @Override
    public Integer getOffset() {
        return offset;
    }

    @Override
    public Integer getRows() {
        return rows;
    }

}
