package com.app.recipefarm.model.base;

public class Pagination {
    public int currentPage;
    public int pageSize;

    public Pagination(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
}