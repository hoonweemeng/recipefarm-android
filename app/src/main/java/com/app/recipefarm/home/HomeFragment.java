package com.app.recipefarm.home;

import static com.app.recipefarm.utility.Constants.latestRecipeEndpoint;

import android.os.Bundle;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.recipefarm.browse.BrowseFragment;

public class HomeFragment extends BrowseFragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initActionBar("Home", false);
    }

    @Override
    public String getRecipeListRequestUrl() {
        return latestRecipeEndpoint;
    }

}