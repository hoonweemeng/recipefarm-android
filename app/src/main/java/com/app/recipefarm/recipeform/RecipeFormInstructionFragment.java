package com.app.recipefarm.recipeform;

import static com.app.recipefarm.utility.Constants.GENERIC_ERROR_MSG;
import static com.app.recipefarm.utility.Constants.RECIPE_IMAGES;
import static com.app.recipefarm.utility.Constants.createRecipeEndpoint;
import static com.app.recipefarm.utility.Constants.updateRecipeEndpoint;
import static com.app.recipefarm.utility.RFFunctions.getHeaders;
import static com.app.recipefarm.utility.RFFunctions.getInvalidEntries;
import static com.app.recipefarm.utility.RFFunctions.isResponseSuccessful;
import static com.app.recipefarm.utility.RFFunctions.responseErrorHandler;
import static com.app.recipefarm.utility.ValidationMethods.validateInstructions;

import android.net.Uri;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.app.recipefarm.R;
import com.app.recipefarm.RFDataManager;
import com.app.recipefarm.core.RFDialog;
import com.app.recipefarm.core.RFFragment;
import com.app.recipefarm.model.base.ValidationModel;
import com.app.recipefarm.model.response.recipe.RecipeFormResponse;
import com.app.recipefarm.recipedetail.RecipeDetailAdapter;
import com.app.recipefarm.utility.NetworkManager;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecipeFormInstructionFragment extends RFFragment {

    private FirebaseStorage storage;
    private StorageReference storageRef;

    private RecipeDetailAdapter recipeDetailAdapter;
    private RecyclerView recyclerView;
    private Button submitBtn;
    private Button addBtn;
    private EditText instructionField;
    private ArrayList<String> instructionList;

    public RecipeFormInstructionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_recipe_form_instruction, container, false);


        // Initialize Firebase Storage
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        recyclerView = mainView.findViewById(R.id.recipeFormRV);
        submitBtn = mainView.findViewById(R.id.recipeformSubmitBtn);
        addBtn = mainView.findViewById(R.id.recipeformInstructionAddBtn);
        instructionField = mainView.findViewById(R.id.recipeformInstructionField);

        submitBtn.setOnClickListener(v -> submit());
        addBtn.setOnClickListener(v -> onClickAdd());

        instructionList = RFDataManager.shared().recipeFormHelper.recipe.getInstructions();

        recipeDetailAdapter = new RecipeDetailAdapter(getContext(), instructionList, id -> {
            // show delete option
        });

        recyclerView.setAdapter(recipeDetailAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return mainView;
    }


    private void onClickAdd() {
        String value = String.valueOf(instructionField.getText());
        List<ValidationModel> validationList = new ArrayList<>();
        validationList.add(validateInstructions(value));
        List<ValidationModel> invalidEntries = getInvalidEntries(validationList);

        if (!invalidEntries.isEmpty()){
            RFDialog dialog = new RFDialog(getContext(), "Error", invalidEntries.get(0).message, null, "Close", null);
            dialog.show();
            return;
        }

        // save to recipeFormHelper
        instructionList.add(value);
        RFDataManager.shared().recipeFormHelper.recipe.setInstructions(instructionList);

        // update data in recyclerview
        recipeDetailAdapter.notifyItemInserted(instructionList.size() - 1);

        //clear text in instruction field
        instructionField.setText("");
    }

    private void submit() {
        // make sure instruction list is not empty
        if (instructionList.isEmpty()){
            RFDialog dialog = new RFDialog(getContext(), "Error", "Instructions are required.", null, "Close", null);
            dialog.show();
            return;
        }

        showLoader(null, false);

        // upload image to firebase, then send save recipe api request
        if (RFDataManager.shared().recipeFormHelper.recipeImageURI != null) {
            // upload image
            uploadImageToFirebase(RFDataManager.shared().recipeFormHelper.recipeImageURI, UUID.randomUUID().toString(), RECIPE_IMAGES);
        }
        else {
            // image already uploaded
            sendSaveRequest();
        }

    }

    private void uploadImageToFirebase(Uri imageUri, String imageName , String folderName) {
        String fileExt = "jpg";
        // Create a reference to the Firebase Storage location
        StorageReference fileReference = storageRef.child(folderName + "/" + imageName + "." + fileExt);

        // Upload the image to Firebase Storage
        fileReference.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Get the download URL of the uploaded file
                    fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        String downloadUrl = uri.toString();

                        //save image name to recipe
                        RFDataManager.shared().recipeFormHelper.recipe.recipeImage = imageName;
                        RFDataManager.shared().recipeFormHelper.recipe.recipeImageExt = fileExt;

                        //save recipe
                        sendSaveRequest();
                    });
                })
                .addOnFailureListener(e -> {
                    // Handle any errors
                    loader.hide();
                    RFDialog dialog = new RFDialog(getContext(), "Error", GENERIC_ERROR_MSG, null, "Close", null);
                    dialog.show();
                });
    }

    private void sendSaveRequest() {
        // check if it is update or create request
        if (RFDataManager.shared().recipeFormHelper.existingRecipe == null) {
            // create
            NetworkManager.getInstance(getContext()).post(
                    createRecipeEndpoint,
                    getHeaders(getContext()),
                    RFDataManager.shared().recipeFormHelper.recipe,
                    RecipeFormResponse.class,
                    new NetworkManager.ResponseCallback<RecipeFormResponse>() {
                        @Override
                        public void onSuccess(RecipeFormResponse response) {
                            loader.hide();
                            parseResponse(response);
                        }

                        @Override
                        public void onError(String error) {
                            loader.hide();
                            RFDialog dialog = new RFDialog(getContext(), "Error", error, null, "Close", null);
                            dialog.show();
                        }
                    }
            );
        }
        else {
            // update
            NetworkManager.getInstance(getContext()).post(
                    updateRecipeEndpoint,
                    getHeaders(getContext()),
                    RFDataManager.shared().recipeFormHelper.recipe,
                    RecipeFormResponse.class,
                    new NetworkManager.ResponseCallback<>() {
                        @Override
                        public void onSuccess(RecipeFormResponse response) {
                            loader.hide();
                            parseResponse(response);
                        }

                        @Override
                        public void onError(String error) {
                            loader.hide();
                            RFDialog dialog = new RFDialog(getContext(), "Error", error, null, "Close", null);
                            dialog.show();
                        }
                    }
            );
        }

    }

    private void parseResponse(RecipeFormResponse response) {
        if (isResponseSuccessful(response)){
            showSuccessDialog();
        }
        else {
            responseErrorHandler(getContext(), response);
        }
    }

    private void showSuccessDialog() {
        String dialogDescription =  "The " + RFDataManager.shared().recipeFormHelper.recipe.title + " recipe has been ";
        if (RFDataManager.shared().recipeFormHelper.existingRecipe == null) {
            dialogDescription += "created.";
        }
        else {
            dialogDescription += "updated.";
        }

        RFDialog dialog = new RFDialog(getContext(), "Success", dialogDescription , null, "Close", new RFDialog.OnDialogActionListener() {

            @Override
            public void onPositiveAction() {

            }

            @Override
            public void onNegativeAction() {
                // close Activity
                getActivity().finish();
            }
        });
        dialog.show();
    }
}