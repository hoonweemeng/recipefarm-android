package com.app.recipefarm.myrecipe;

import static com.app.recipefarm.utility.Constants.userRecipeEndpoint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.recipefarm.RFDataManager;
import com.app.recipefarm.browse.BrowseFragment;
import com.app.recipefarm.model.base.Recipe;
import com.app.recipefarm.myrecipe.viewmodel.FetchMyRecipesViewModel;
import com.app.recipefarm.recipeform.RecipeFormActivity;

public class MyRecipeFragment extends BrowseFragment {

    public MyRecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initActionBar("My Recipes", true);
    }

    @Override
    public String getRecipeListRequestUrl() {
        return userRecipeEndpoint;
    }

    @Override
    public void initFetchViewModel() {
        fetchRecipesViewModel = new FetchMyRecipesViewModel(getContext(), getRecipeListRequestUrl(), responseListener);
    }

    @Override
    public void onSelectRecipe(Recipe recipe) {
        RFDataManager.shared().recipeFormHelper.existingRecipe = recipe;
        Intent editIntent = new Intent(getActivity(), RecipeFormActivity.class);
        startActivity(editIntent);
    }
    
}