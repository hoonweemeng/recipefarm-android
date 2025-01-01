package com.app.recipefarm.onboarding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.recipefarm.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    private View mainView;
    private ImageView backBtn;
    private TextView pageTitle;


    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.onboarding_fragment_register, container, false);

        pageTitle = mainView.findViewById(R.id.page_title);
        pageTitle.setText("Register");
        backBtn = mainView.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> requireActivity().onBackPressed());

        return mainView;
    }
}