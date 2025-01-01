package com.app.recipefarm.onboarding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.recipefarm.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    private View mainView;
    private ImageView backBtn;
    private TextView pageTitle;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.onboarding_fragment_login, container, false);
        pageTitle = mainView.findViewById(R.id.page_title);
        pageTitle.setText("Login");
        backBtn = mainView.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> requireActivity().onBackPressed());


        return mainView;
    }
}