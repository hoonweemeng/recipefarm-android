package com.app.recipefarm.recipeform;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.recipefarm.R;
import com.app.recipefarm.RFDataManager;
import com.app.recipefarm.core.RFFragment;

import java.io.FileNotFoundException;

public class RecipeFormMainFragment extends RFFragment {

    private RelativeLayout recipeImageLayout;
    private View recipeImageOverlay;
    private ImageView recipeImageView;
    private ImageView cameraIcon;
    private TextView cameraText;

    private static final int PICK_IMAGE_REQUEST = 1;

    public RecipeFormMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_recipe_form_main, container, false);

        recipeImageOverlay = mainView.findViewById(R.id.recipeFormImageOverlay);
        recipeImageView = mainView.findViewById(R.id.recipeFormImage);
        cameraIcon = mainView.findViewById(R.id.recipeFormCameraIcon);
        cameraText = mainView.findViewById(R.id.recipeFormCameraText);
        cameraText = mainView.findViewById(R.id.recipeFormCameraText);
        recipeImageLayout = mainView.findViewById(R.id.recipeFormImageLayout);

        recipeImageLayout.setOnClickListener(v -> openImagePicker());

        initRecipeImage();

        return mainView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            // Persist access to the selected image across app restarts
            getContext().getContentResolver().takePersistableUriPermission(selectedImageUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // Set the selected image to an ImageView
            recipeImageView.setImageURI(selectedImageUri);

            // Save the URI
            RFDataManager.shared().recipeFormHelper.recipeImageURI = selectedImageUri;
            updateImageCardStyle();

        }
    }

    private void initRecipeImage() {
        if (RFDataManager.shared().recipeFormHelper.recipe!= null && RFDataManager.shared().recipeFormHelper.recipe.recipeImage != null) {
            updateImageCardStyle();
            // fetch and display image

        }
        else {
            cameraText.setTextColor(getContext().getColor(R.color.light_grey));
            cameraIcon.setImageTintList(ColorStateList.valueOf(getContext().getColor(R.color.light_grey)));
            recipeImageOverlay.setVisibility(View.GONE);
            recipeImageView.setVisibility(View.GONE);
        }
    }

    // call this when there is an image
    private void updateImageCardStyle() {
        cameraText.setTextColor(getContext().getColor(R.color.white));
        cameraText.setText("Replace photo");
        cameraIcon.setImageTintList(ColorStateList.valueOf(getContext().getColor(R.color.white)));
        recipeImageOverlay.setVisibility(View.VISIBLE);
        recipeImageView.setVisibility(View.VISIBLE);
    }


    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

}