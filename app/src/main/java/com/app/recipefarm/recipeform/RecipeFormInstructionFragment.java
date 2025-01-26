package com.app.recipefarm.recipeform;

import static com.app.recipefarm.utility.Constants.GENERIC_ERROR_MSG;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.recipefarm.R;
import com.app.recipefarm.core.RFDialog;
import com.app.recipefarm.core.RFFragment;
import com.google.firebase.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class RecipeFormInstructionFragment extends RFFragment {

    private FirebaseStorage storage;
    private StorageReference storageRef;

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

        return mainView;
    }

    private void uploadImageToFirebase(Uri imageUri, String imageName , String folderName) {
        // Create a reference to the Firebase Storage location
        StorageReference fileReference = storageRef.child(folderName + "/" + imageName + ".jpg");

        // Upload the image to Firebase Storage
        fileReference.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Get the download URL of the uploaded file
                    fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        String downloadUrl = uri.toString();
                        // You can now use the download URL (e.g., save it to Firestore, display in app)
                        //TODO: save recipe
                    });
                })
                .addOnFailureListener(e -> {
                    // Handle any errors
                    RFDialog dialog = new RFDialog(getContext(), "Error", GENERIC_ERROR_MSG, null, "Close", null);
                    dialog.show();
                });
    }
}