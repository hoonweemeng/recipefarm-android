package com.app.recipefarm.recipedetail.viewmodel;

import static com.app.recipefarm.utility.Constants.detailUserEndpoint;
import static com.app.recipefarm.utility.Constants.getBookmarkEndpoint;
import static com.app.recipefarm.utility.RFFunctions.getHeaders;
import static com.app.recipefarm.utility.RFFunctions.isResponseSuccessful;

import android.content.Context;

import com.app.recipefarm.utility.CompleteListener;
import com.app.recipefarm.model.base.IdModel;
import com.app.recipefarm.model.base.Recipe;
import com.app.recipefarm.model.response.bookmark.BookmarkDetailResponse;
import com.app.recipefarm.model.response.user.UserDetailResponse;
import com.app.recipefarm.utility.NetworkManager;

public class FetchRecipeDetailViewModel {
    // handle fetch request of recipedetail
    private final Context context;
    public BookmarkDetailResponse bookmarkDetailResponse = null;
    public UserDetailResponse userDetailResponse = null;
    private final CompleteListener listener;
    private Recipe recipe;


    public FetchRecipeDetailViewModel(Context context, Recipe recipe, CompleteListener listener) {
        this.context = context;
        this.recipe = recipe;
        this.listener = listener;
    }

    public void sendRequest() {
        if (bookmarkDetailResponse == null || userDetailResponse == null) {
            if (bookmarkDetailResponse == null && userDetailResponse == null) {
                fetchBookmark();
                fetchUserDetail();
            }
        }
        else {
            listener.onComplete(true);
        }
    }

    private void fetchBookmark() {
        // fetch bookmark
        NetworkManager.getInstance(context).post(getBookmarkEndpoint, getHeaders(context), new IdModel(recipe.recipeId), BookmarkDetailResponse.class, new NetworkManager.ResponseCallback<>() {

            @Override
            public void onSuccess(BookmarkDetailResponse response) {
                bookmarkDetailResponse = response;
                if (isResponseSuccessful(response)) {
                    sendRequest();
                }
                else {
                    listener.onComplete(false);
                }
            }

            @Override
            public void onError(String error) {
                listener.onComplete(false);
            }
        });
    }

    private void fetchUserDetail() {
        //fetch user detail
        NetworkManager.getInstance(context).post(detailUserEndpoint, getHeaders(context), new IdModel(recipe.userId), UserDetailResponse.class, new NetworkManager.ResponseCallback<>() {
            @Override
            public void onSuccess(UserDetailResponse response) {
                userDetailResponse = response;
                if (isResponseSuccessful(response)) {
                    sendRequest();
                }
                else {
                    listener.onComplete(false);
                }
            }

            @Override
            public void onError(String error) {
                listener.onComplete(false);
            }
        });
    }

}
