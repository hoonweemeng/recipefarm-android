package com.app.recipefarm.recipeform;

import static android.app.Activity.RESULT_OK;

import static com.app.recipefarm.utility.RFFunctions.getInvalidEntries;
import static com.app.recipefarm.utility.ValidationMethods.validateDescription;
import static com.app.recipefarm.utility.ValidationMethods.validateDuration;
import static com.app.recipefarm.utility.ValidationMethods.validateRecipeImage;
import static com.app.recipefarm.utility.ValidationMethods.validateServings;
import static com.app.recipefarm.utility.ValidationMethods.validateTitle;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.recipefarm.R;
import com.app.recipefarm.RFDataManager;
import com.app.recipefarm.core.RFDialog;
import com.app.recipefarm.core.RFFragment;
import com.app.recipefarm.model.base.ValidationModel;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class RecipeFormMainFragment extends RFFragment {

    private RelativeLayout recipeImageLayout;
    private View recipeImageOverlay;
    private ImageView recipeImageView;
    private ImageView cameraIcon;
    private TextView cameraText;
    private EditText titleField;
    private EditText descriptionField;
    private EditText durationField;
    private EditText servingsField;
    private Button nextBtn;

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
        titleField = mainView.findViewById(R.id.recipeFormTitle);
        descriptionField = mainView.findViewById(R.id.recipeFormDescription);
        durationField = mainView.findViewById(R.id.recipeFormDuration);
        servingsField = mainView.findViewById(R.id.recipeFormServings);
        nextBtn = mainView.findViewById(R.id.recipeformNextBtn);

        recipeImageLayout.setOnClickListener(v -> openImagePicker());
        nextBtn.setOnClickListener(v -> onSelectNext());

        initRecipeImage();

        if (RFDataManager.shared().recipeFormHelper.existingRecipe != null) {
            fillInExistingData();
        }

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

    private void onSelectNext() {
        //retrieve edit text value
        String title =String.valueOf( titleField.getText()).trim();
        String description =String.valueOf( descriptionField.getText()).trim();
        String durationStr =String.valueOf( durationField.getText()).trim();
        String servingsStr =String.valueOf( servingsField.getText()).trim();

        // validate
        List<ValidationModel> validationList = new ArrayList<>();
        validationList.add(validateRecipeImage());
        validationList.add(validateTitle(title));
        validationList.add(validateDescription(description));
        validationList.add(validateDuration(durationStr));
        validationList.add(validateServings(servingsStr));
        List<ValidationModel> invalidEntries = getInvalidEntries(validationList);

        if (!invalidEntries.isEmpty()){
            RFDialog dialog = new RFDialog(getContext(), "Error", invalidEntries.get(0).message, null, "Close", null);
            dialog.show();
            return;
        }

        // convert string values to int if needed
        int duration = Integer.parseInt(durationStr);
        int servings = Integer.parseInt(servingsStr);

        // save in recipe form helper
        RFDataManager.shared().recipeFormHelper.recipe.title = title;
        RFDataManager.shared().recipeFormHelper.recipe.description = description;
        RFDataManager.shared().recipeFormHelper.recipe.duration = duration;
        RFDataManager.shared().recipeFormHelper.recipe.servings = servings;

        // navigate to ingredients page
        navigateToIngredientsPage();
    }

    private void fillInExistingData() {
        titleField.setText(RFDataManager.shared().recipeFormHelper.recipe.title);
        descriptionField.setText(RFDataManager.shared().recipeFormHelper.recipe.description);
        durationField.setText(String.valueOf(RFDataManager.shared().recipeFormHelper.recipe.duration));
        servingsField.setText(String.valueOf(RFDataManager.shared().recipeFormHelper.recipe.servings));
    }

    private void initRecipeImage() {
        if (RFDataManager.shared().recipeFormHelper.recipeImageURI != null) {
            // display recently chosen image
            updateImageCardStyle();
            recipeImageView.setImageURI(RFDataManager.shared().recipeFormHelper.recipeImageURI);

        }
        else if (RFDataManager.shared().recipeFormHelper.recipe != null && RFDataManager.shared().recipeFormHelper.recipe.recipeImage != null) {
            // display image from firebase
            updateImageCardStyle();

            // fetch and display image
            StorageReference storageRef = FirebaseStorage.getInstance().getReference(RFDataManager.shared().recipeFormHelper.recipe.getImagePath());

            Glide.with(getContext())
                    .load(storageRef)
                    .into(recipeImageView);
        }
        else {
            // no image
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

    private void navigateToIngredientsPage() {
        RecipeFormIngredientFragment ingredientFragment = new RecipeFormIngredientFragment();
        navigateToAnotherFragment(ingredientFragment, R.id.recipeFormFrame);
    }

}