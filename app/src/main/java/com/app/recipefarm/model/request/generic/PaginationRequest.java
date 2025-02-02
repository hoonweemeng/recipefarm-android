package com.app.recipefarm.model.request.generic;

import com.app.recipefarm.model.base.Pagination;

public class PaginationRequest {
    public Pagination pagination;

    public PaginationRequest(Pagination pagination) {
        this.pagination = pagination;
    }
}