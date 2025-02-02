package com.app.recipefarm;

import com.app.recipefarm.model.base.User;
import com.app.recipefarm.recipeform.RecipeFormHelper;

public class RFDataManagerData{

    // place data here instead of RFDataManager for easy reset
    public MainActivityHelper mainActivityHelper = new MainActivityHelper();

    public User user = null;
    public RecipeFormHelper recipeFormHelper = new RecipeFormHelper();
}
