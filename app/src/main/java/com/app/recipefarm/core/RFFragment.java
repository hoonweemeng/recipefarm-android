package com.app.recipefarm.core;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.app.recipefarm.MainActivity;
import com.app.recipefarm.R;
import com.app.recipefarm.utility.RFLoader;

// Core Fragment with shared methods and variables
public class RFFragment extends Fragment {

    public View mainView;
    private ImageView backBtn;
    private TextView pageTitle;
    public RFLoader loader;

    public void showLoader(String errorMessage){
        loader = new RFLoader(getContext());
        loader.show(errorMessage);
    }

    public void initActionBar(String title) {
        try {
            pageTitle = mainView.findViewById(R.id.page_title);
            pageTitle.setText(title);
            backBtn = mainView.findViewById(R.id.backBtn);
            backBtn.setOnClickListener(v -> requireActivity().onBackPressed());
        }
        catch (Exception exception){
            // Action Bar not found
        }
    }

    public void backToHome() {
        if (requireActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) requireActivity();
            mainActivity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            mainActivity.initMainBody();
        }
        else {
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            requireActivity().finish();
        }
    }

}
