package com.app.recipefarm.onboarding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.recipefarm.R;
import com.app.recipefarm.core.RFFragment;

public class GetStartedFragment extends RFFragment {

    private Button getStartedBtn;

    public GetStartedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.onboarding_fragment_get_started, container, false);
        // Set up a button to navigate to SecondFragment
        getStartedBtn = mainView.findViewById(R.id.get_started_btn);
        getStartedBtn.setOnClickListener(v -> navigateToLogin());

        return mainView;
    }

    private void navigateToLogin() {
        Fragment loginFragment = new LoginFragment();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.onboarding_frame, loginFragment);
        transaction.addToBackStack(null); // Add this transaction to the back stack
        transaction.commit();
    }
}