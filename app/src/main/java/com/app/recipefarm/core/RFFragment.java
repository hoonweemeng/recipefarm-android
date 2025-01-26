package com.app.recipefarm.core;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.recipefarm.MainActivity;
import com.app.recipefarm.R;
import com.app.recipefarm.onboarding.RegisterFragment;

// Core Fragment with shared methods and variables
public class RFFragment extends Fragment {

    public View mainView;
    private ImageView backBtn;
    private TextView pageTitle;
    public RFLoader loader;

    public void showLoader(String message){
        loader = new RFLoader(getContext());
        loader.show(message);
    }

    public void initActionBar(String title, Boolean requireBackBtn) {
        try {
            pageTitle = mainView.findViewById(R.id.page_title);
            pageTitle.setText(title);
            backBtn = mainView.findViewById(R.id.backBtn);
            backBtn.setOnClickListener(v -> requireActivity().onBackPressed());

            if (requireBackBtn) {
                backBtn.setVisibility(View.VISIBLE);
            }
            else {
                backBtn.setVisibility(View.GONE);
            }
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
            Intent intent = new Intent(getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            requireActivity().finish();
        }
    }

    public void navigateToAnotherFragment(Fragment fragment, int containerViewId) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerViewId, fragment);
        transaction.addToBackStack(null); // Add this transaction to the back stack
        transaction.commit();
    }

}
