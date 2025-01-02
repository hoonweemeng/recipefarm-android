package com.app.recipefarm.core;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.recipefarm.R;

// Core Fragment with shared methods and variables
public class RFFragment extends Fragment {

    public View mainView;
    private ImageView backBtn;
    private TextView pageTitle;

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


}
