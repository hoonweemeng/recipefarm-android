package com.app.recipefarm.browse;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.app.recipefarm.R;
import com.app.recipefarm.browse.viewmodel.FetchRecipesViewModel;
import com.app.recipefarm.core.RFDialog;
import com.app.recipefarm.core.RFFragment;
import com.app.recipefarm.model.base.Recipe;
import com.app.recipefarm.recipedetail.RecipeDetailActivity;
import com.app.recipefarm.utility.CompleteListener;
import com.google.gson.Gson;

import java.util.ArrayList;


public class BrowseFragment extends RFFragment {

    public ArrayList<Recipe> recipeList;
    public NestedScrollView nestedSv;
    public ProgressBar progressBar;
    public RecyclerView recyclerView;
    public BrowseAdapter browseAdapter;
    public FetchRecipesViewModel fetchRecipesViewModel;
    public SwipeRefreshLayout swipeRefreshLayout;

    public CompleteListener responseListener;

    public BrowseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_browse, container, false);
        swipeRefreshLayout = mainView.findViewById(R.id.browseSwipeRefresh);
        nestedSv = mainView.findViewById(R.id.browseNestedSv);
        progressBar = mainView.findViewById(R.id.browseLoading);
        recyclerView = mainView.findViewById(R.id.brosweRecyclerView);

        //to check if user reached the bottom
        nestedSv.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (scrollView, scrollX, scrollY, oldScrollX, oldScrollY) -> {

            View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
            int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

            // if diff is zero, then the bottom has been reached
            if (diff < 50) {
                // fetch for more recipes if required
                if (!fetchRecipesViewModel.onLastPage && !fetchRecipesViewModel.isWaitingForResponse){
                    //load next page
                    fetchRecipes();
                }
            }
        });

        //refresh page by using Init()
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                refreshData();
            }
        });

        responseListener = success -> {

            // handle response
            if (success) {
                handleSuccessfulResponse();
            }
            else {

                // hide progress bar
                progressBar.setVisibility(View.GONE);

                // show error message
                RFDialog dialog = new RFDialog(getContext(), "Error", "Failed to load recipes", "Retry", "Close", new RFDialog.OnDialogActionListener() {
                    @Override
                    public void onPositiveAction() {
                        fetchRecipesViewModel.fetchRecipes();
                    }

                    @Override
                    public void onNegativeAction() {}
                });
                dialog.show();
            }
        };

        refreshData();
        return mainView;
    }

    // to be overridden
    public String getRecipeListRequestUrl() {
        return "";
    }

    // to be overridden
    public void onSelectRecipe(Recipe recipe) {
        // default - open recipe detail page

        // send data to recipe detail page
        // convert to json
        Gson gson = new Gson();
        String json = gson.toJson(recipe);

        Intent intent = new Intent(getContext(), RecipeDetailActivity.class);
        intent.putExtra("recipe", json);
        startActivity(intent);
    }

    // refresh data
    public void refreshData() {

        initFetchViewModel();
        recipeList = fetchRecipesViewModel.recipeList;

        browseAdapter = new BrowseAdapter(getContext(), recipeList, recipe -> {
            onSelectRecipe(recipe);
        });

        int numberOfColumns = 2; // Change this for different column counts
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        recyclerView.setAdapter(browseAdapter);

        fetchRecipes();
    }

    public void fetchRecipes() {
        progressBar.setVisibility(View.VISIBLE);
        fetchRecipesViewModel.fetchRecipes();
    }

    public void initFetchViewModel() {
        fetchRecipesViewModel = new FetchRecipesViewModel(getContext(), getRecipeListRequestUrl(), responseListener);
    }

    public void handleSuccessfulResponse() {
        int noOfItemAffected = fetchRecipesViewModel.recipeListResponse.data.size();
        // update recyclerView
        browseAdapter.notifyItemRangeChanged(recipeList.size() - noOfItemAffected, noOfItemAffected);

        // if is last page
        if (fetchRecipesViewModel.onLastPage) {
            // hide progress bar
            progressBar.setVisibility(View.GONE);
        }
    }
}