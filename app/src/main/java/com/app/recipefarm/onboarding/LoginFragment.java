package com.app.recipefarm.onboarding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.recipefarm.R;
import com.app.recipefarm.core.RFFragment;

public class LoginFragment extends RFFragment {
    private LinearLayout registerText;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.onboarding_fragment_login, container, false);

        initActionBar("Login");
        registerText = mainView.findViewById(R.id.sign_up_text);
        registerText.setOnClickListener(l -> navigateToRegister());

        return mainView;
    }


    private void navigateToRegister() {
        Fragment registerFragment = new RegisterFragment();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.onboarding_frame, registerFragment);
        transaction.addToBackStack(null); // Add this transaction to the back stack
        transaction.commit();
    }
}