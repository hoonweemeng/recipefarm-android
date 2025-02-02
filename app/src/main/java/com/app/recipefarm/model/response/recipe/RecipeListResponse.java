package com.app.recipefarm.model.response.recipe;

import com.app.recipefarm.model.base.Recipe;
import com.app.recipefarm.model.response.generic.RFResponse;

import java.util.ArrayList;

public class RecipeListResponse extends RFResponse {
    public ArrayList<Recipe> data;
}
