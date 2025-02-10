package com.app.recipefarm.browse.viewmodel;

import static com.app.recipefarm.utility.RFFunctions.getHeaders;
import static com.app.recipefarm.utility.RFFunctions.isResponseSuccessful;

import android.content.Context;

import com.app.recipefarm.utility.CompleteListener;
import com.app.recipefarm.model.base.Pagination;
import com.app.recipefarm.model.base.Recipe;
import com.app.recipefarm.model.request.generic.PaginationRequest;
import com.app.recipefarm.model.response.recipe.RecipeListResponse;
import com.app.recipefarm.utility.NetworkManager;

import java.util.ArrayList;

public class FetchRecipesViewModel {
    // handle fetch request of recipes

    public final int pageSize = 10;
    public int currentPage = 0;
    public boolean onLastPage = false;
    public Context context;
    public String fetchRecipesUrl;
    public RecipeListResponse recipeListResponse;
    public CompleteListener listener;
    public NetworkManager.ResponseCallback<RecipeListResponse> responseCallback;
    public ArrayList<Recipe> recipeList;
    public Boolean isWaitingForResponse = false;


    public FetchRecipesViewModel(Context context, String fetchRecipesUrl, CompleteListener listener) {
        this.context = context;
        this.fetchRecipesUrl = fetchRecipesUrl;
        this.listener = listener;

        recipeList = new ArrayList<>();

        responseCallback =  new NetworkManager.ResponseCallback<>() {
            @Override
            public void onSuccess(RecipeListResponse response) {
                isWaitingForResponse = false;
                recipeListResponse = response;
                parseResponse();
            }

            @Override
            public void onError(String error) {
                isWaitingForResponse = false;
                listener.onComplete(false);
            }
        };
    }

    public void fetchRecipes() {
        // construct request
        PaginationRequest fetchRecipeRequest = new PaginationRequest(getPaginationModel());

        //fetch recipes
        NetworkManager.getInstance(context).post(fetchRecipesUrl, getHeaders(context), fetchRecipeRequest, RecipeListResponse.class, responseCallback);
        isWaitingForResponse = true;
    }

    public Pagination getPaginationModel() {
        int nextPage = currentPage + 1;
        return new Pagination(nextPage, pageSize);
    }


    private void parseResponse() {
        if (isResponseSuccessful(recipeListResponse)) {
            // check if the current page is the last
            if (recipeListResponse.data.isEmpty()) {
                // the previous page that was fetched was the last
                onLastPage = true;
            }
            else if (recipeListResponse.data.size() % pageSize != 0) {
                onLastPage = true;
                currentPage += 1;
            }
            else {
                currentPage += 1;
            }
            recipeList.addAll(recipeListResponse.data);
            listener.onComplete(true);
        }
        else {
            listener.onComplete(false);
        }
    }

}
