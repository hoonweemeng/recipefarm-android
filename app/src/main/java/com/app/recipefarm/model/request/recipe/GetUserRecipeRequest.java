package com.app.recipefarm.model.request.recipe;

import com.app.recipefarm.model.base.Pagination;
import com.app.recipefarm.model.request.generic.PaginationRequest;

public class GetUserRecipeRequest extends PaginationRequest {

    public String userId;
    public GetUserRecipeRequest(String userId, Pagination pagination) {
        super(pagination);
        this.userId = userId;
    }


}
