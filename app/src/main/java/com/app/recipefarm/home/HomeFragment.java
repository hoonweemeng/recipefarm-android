package com.app.recipefarm.home;

import static com.app.recipefarm.utility.Constants.latestRecipeEndpoint;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.recipefarm.R;
import com.app.recipefarm.browse.BrowseFragment;

public class HomeFragment extends BrowseFragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       super.onCreateView(inflater,container,savedInstanceState);

       initActionBar("Home", false);
        return mainView;
    }

    @Override
    public String getRecipeListRequestUrl() {
        return latestRecipeEndpoint;
    }

    @Override
    public void onSelectRecipe(String recipeId) {

    }


}