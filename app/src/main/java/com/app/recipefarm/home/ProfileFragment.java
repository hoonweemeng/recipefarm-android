package com.app.recipefarm.home;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.recipefarm.R;
import com.app.recipefarm.RFDataManager;
import com.app.recipefarm.action.ActionAdapter;
import com.app.recipefarm.action.ActionListener;
import com.app.recipefarm.action.viewmodels.ActionViewModel;
import com.app.recipefarm.core.RFFragment;
import com.app.recipefarm.recipeform.RecipeFormActivity;
import com.app.recipefarm.utility.RFFunctions;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ProfileFragment extends RFFragment {

    private TextView tvUsername;
    private ImageView profileImage;
    private RecyclerView actionRecyclerView;
    private final ArrayList<ActionViewModel> actionList;
    private ActionAdapter actionAdapter;


    public ProfileFragment() {
        // Required empty public constructor
        actionList = new ArrayList<>();
        actionList.add(ActionType.CREATE_RECIPE.getActionViewModel());
        actionList.add(ActionType.MY_RECIPES.getActionViewModel());
        actionList.add(ActionType.LOGOUT.getActionViewModel());
        // actionList.add(ActionType.DELETE_ACCOUNT.getActionViewModel());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_profile, container, false);

        tvUsername = mainView.findViewById(R.id.profileUsername);
        profileImage = mainView.findViewById(R.id.recipeDetailUserImage);
        actionRecyclerView = mainView.findViewById(R.id.brosweRecyclerView);

        tvUsername.setText(RFDataManager.shared().user.username);

        // load profile image if it exist
        if (RFDataManager.shared().user.profileImage != null) {
            // get image from firebase and set it to imageview
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(RFDataManager.shared().user.getImagePath());
            Glide.with(getContext())
                    .load(storageReference)
                    .into(profileImage);
        }

        actionAdapter = new ActionAdapter(getContext(), actionList, new ActionListener() {
            @Override
            public void onSelect(int id) {
                // when an action is click
                onActionClick(ActionType.fromOrdinal(id));
            }
        });
        actionRecyclerView.setAdapter(actionAdapter);
        actionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return mainView;
    }

    private void onActionClick(ActionType actionType) {
        switch (actionType) {
            case CREATE_RECIPE:
                Intent createIntent = new Intent(getActivity(), RecipeFormActivity.class);
                startActivity(createIntent);
                break;

            case MY_RECIPES:
                break;

            case LOGOUT:
                RFFunctions.logout(getActivity());
                break;

            case DELETE_ACCOUNT:
                break;
        }
    }

    public enum ActionType {
        CREATE_RECIPE("Create Recipe"),
        MY_RECIPES("My Recipes"),
        LOGOUT("Logout"),
        DELETE_ACCOUNT("Delete Account");

        private final String title;

        ActionType(String title) {
            this.title = title;
        }

        // Method to convert ordinal to Status
        public static ActionType fromOrdinal(int ordinal) {
            if (ordinal < 0 || ordinal >= values().length) {
                throw new IllegalArgumentException("Invalid ordinal for Status: " + ordinal);
            }
            return values()[ordinal];
        }

        public ActionViewModel getActionViewModel() {
            return new ActionViewModel(ordinal(),title);
        }
    }
}