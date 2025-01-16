package com.app.recipefarm.onboarding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.recipefarm.MainActivity;
import com.app.recipefarm.R;
import com.app.recipefarm.core.RFFragment;

public class RegisterFragment extends RFFragment {

    private Button registerBtn;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.onboarding_fragment_register, container, false);
        initActionBar("Register");
        registerBtn = mainView.findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(v -> onSubmit());
        return mainView;
    }

    private void onSubmit() {
        backToHome();
    }

}