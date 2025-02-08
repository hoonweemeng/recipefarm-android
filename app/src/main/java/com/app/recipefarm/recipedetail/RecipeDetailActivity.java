package com.app.recipefarm.recipedetail;

import static com.app.recipefarm.utility.Constants.createBookmarkEndpoint;
import static com.app.recipefarm.utility.Constants.deleteBookmarkEndpoint;
import static com.app.recipefarm.utility.RFFunctions.showGenericErrorMessage;
import static com.app.recipefarm.utility.RFFunctions.getHeaders;
import static com.app.recipefarm.utility.RFFunctions.isResponseSuccessful;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.recipefarm.R;
import com.app.recipefarm.core.RFActivity;
import com.app.recipefarm.core.RFDialog;
import com.app.recipefarm.model.base.Bookmark;
import com.app.recipefarm.model.base.IdModel;
import com.app.recipefarm.model.base.Recipe;
import com.app.recipefarm.model.request.generic.EmptyRequest;
import com.app.recipefarm.model.response.bookmark.BookmarkDetailResponse;
import com.app.recipefarm.model.response.generic.RFResponse;
import com.app.recipefarm.recipedetail.viewmodel.FetchRecipeDetailViewModel;
import com.app.recipefarm.utility.CompleteListener;
import com.app.recipefarm.utility.NetworkManager;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

public class RecipeDetailActivity extends RFActivity {

    private Recipe recipe;
    private ImageView recipeImageView;
    private ImageView userImageView;
    private TextView titleTv;
    private TextView paxTv;
    private TextView durationTv;
    private TextView descriptionTv;
    private TextView usernameTv;
    private RecyclerView ingredientRV;
    private RecyclerView instructionRV;
    private FloatingActionButton bookmarkBtn;
    private RecipeDetailAdapter instructionsAdapter;
    private RecipeDetailAdapter ingredientAdapter;
    private CompleteListener responseListener;
    private FetchRecipeDetailViewModel fetchRecipeDetailViewModel;
    private Bookmark bookmark;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initActionBar("Recipe", true);
        showLoader(null, true);

        recipeImageView = findViewById(R.id.recipeDetailImage);
        userImageView = findViewById(R.id.recipeDetailUserImage);
        titleTv = findViewById(R.id.recipeDetailTitle);
        paxTv = findViewById(R.id.recipeDetailPax);
        durationTv = findViewById(R.id.recipeDetailDuration);
        descriptionTv = findViewById(R.id.recipeDetailDescription);
        usernameTv = findViewById(R.id.recipeDetailUsername);
        ingredientRV = findViewById(R.id.recipeDetailIngredientRV);
        instructionRV = findViewById(R.id.recipeDetailInstructionRV);
        bookmarkBtn = findViewById(R.id.recipeDetailBookmark);

        // get recipe from Intent.
        String json = getIntent().getStringExtra("recipe");
        if (json != null) {
            // convert json back to recipe class
            Gson gson = new Gson();
            recipe = gson.fromJson(json, Recipe.class);
        }

        responseListener = success -> {
            if (success) {
                loader.hide();
                bookmark = fetchRecipeDetailViewModel.bookmarkDetailResponse.data;
                fillRecipeDetails();
            }
            else {
                loader.hide();
                showGenericErrorMessage(this);
            }
        };

        bookmarkBtn.setOnClickListener(v -> {
            showLoader(null, false);
            if (bookmark == null) {
                addBookmark();
            }
            else {
                removeBookmark();
            }
        });

        fetchRecipeDetail();
    }

    public void fetchRecipeDetail() {
        fetchRecipeDetailViewModel = new FetchRecipeDetailViewModel(this, recipe, responseListener);
        fetchRecipeDetailViewModel.sendRequest();
    }

    public void fillRecipeDetails() {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference(recipe.getImagePath());
        Glide.with(this)
                .load(storageRef)
                .into(recipeImageView);

        titleTv.setText(recipe.title);
        paxTv.setText(recipe.servings + " pax");
        durationTv.setText(recipe.getDuration());
        descriptionTv.setText(recipe.description);

        ingredientAdapter = new RecipeDetailAdapter(this, recipe.getIngredients(), null);
        ingredientRV.setAdapter(ingredientAdapter);
        ingredientRV.setLayoutManager(new LinearLayoutManager(this));

        instructionsAdapter = new RecipeDetailAdapter(this, recipe.getInstructions(), null);
        instructionRV.setAdapter(instructionsAdapter);
        instructionRV.setLayoutManager(new LinearLayoutManager(this));

        usernameTv.setText(fetchRecipeDetailViewModel.userDetailResponse.data.username);

        if (fetchRecipeDetailViewModel.userDetailResponse.data.profileImage != null) {
            storageRef = FirebaseStorage.getInstance().getReference(fetchRecipeDetailViewModel.userDetailResponse.data.getImagePath());
            Glide.with(this)
                    .load(storageRef)
                    .into(userImageView);
        }

        setBookmarkBtnStyle();

    }

    private void setBookmarkBtnStyle() {
        if (bookmark == null) {
            bookmarkBtn.setImageResource(R.drawable.ic_bookmark);
        }
        else {
            bookmarkBtn.setImageResource(R.drawable.ic_bookmark_added);
        }
    }

    public void removeBookmark() {
        Context context = this;
        NetworkManager.getInstance(this).post(deleteBookmarkEndpoint, getHeaders(this), new IdModel(bookmark.bookmarkId), RFResponse.class, new NetworkManager.ResponseCallback<>() {
            @Override
            public void onSuccess(RFResponse response) {
                loader.hide();
                if (isResponseSuccessful(response)) {
                    bookmark = null;
                    setBookmarkBtnStyle();
                }
                else {
                    RFDialog dialog = new RFDialog(context, "Error", "Unable to delete bookmark.", null, "Close", null);
                    dialog.show();
                }
            }

            @Override
            public void onError(String error) {
                loader.hide();
                RFDialog dialog = new RFDialog(context, "Error", "Unable to delete bookmark.", null, "Close", null);
                dialog.show();
            }
        });
    }

    public void addBookmark() {
        Context context = this;
        NetworkManager.getInstance(this).post(createBookmarkEndpoint, getHeaders(this), new IdModel(recipe.recipeId), BookmarkDetailResponse.class, new NetworkManager.ResponseCallback<>() {

            @Override
            public void onSuccess(BookmarkDetailResponse response) {
                loader.hide();
                if (isResponseSuccessful(response)) {
                    bookmark = response.data;
                    setBookmarkBtnStyle();
                }
                else {
                    RFDialog dialog = new RFDialog(context, "Error", "Unable to add bookmark.", null, "Close", null);
                    dialog.show();
                }
            }

            @Override
            public void onError(String error) {
                loader.hide();
                RFDialog dialog = new RFDialog(context, "Error", "Unable to add bookmark.", null, "Close", null);
                dialog.show();
            }
        });
    }

}