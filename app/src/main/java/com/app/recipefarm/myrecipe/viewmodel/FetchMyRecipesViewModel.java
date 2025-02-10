package com.app.recipefarm.myrecipe.viewmodel;

import static com.app.recipefarm.utility.RFFunctions.getHeaders;

import android.content.Context;

import com.app.recipefarm.RFDataManager;
import com.app.recipefarm.browse.viewmodel.FetchRecipesViewModel;
import com.app.recipefarm.model.request.generic.PaginationRequest;
import com.app.recipefarm.model.request.recipe.GetUserRecipeRequest;
import com.app.recipefarm.model.response.recipe.RecipeListResponse;
import com.app.recipefarm.utility.CompleteListener;
import com.app.recipefarm.utility.NetworkManager;

public class FetchMyRecipesViewModel extends FetchRecipesViewModel {
    public FetchMyRecipesViewModel(Context context, String fetchRecipesUrl, CompleteListener listener) {
        super(context, fetchRecipesUrl, listener);
    }

    @Override
    public void fetchRecipes() {
        // construct request
        GetUserRecipeRequest fetchRecipeRequest = new GetUserRecipeRequest(RFDataManager.shared().user.userId, getPaginationModel());

        //fetch recipes
        NetworkManager.getInstance(context).post(fetchRecipesUrl, getHeaders(context), fetchRecipeRequest, RecipeListResponse.class, responseCallback);
        isWaitingForResponse = true;
    }
}
