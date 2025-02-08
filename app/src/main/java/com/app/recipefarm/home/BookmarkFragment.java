package com.app.recipefarm.home;

import static com.app.recipefarm.utility.Constants.bookmarkRecipeEndpoint;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.recipefarm.browse.BrowseFragment;

public class BookmarkFragment extends BrowseFragment {

    public BookmarkFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initActionBar("Bookmark", false);
    }

    @Override
    public String getRecipeListRequestUrl() {
        return bookmarkRecipeEndpoint;
    }

}