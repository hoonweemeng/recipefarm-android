package com.app.recipefarm.core;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.recipefarm.R;

// Core Activity with shared methods and variables
public class RFActivity extends AppCompatActivity {
    private ImageView backBtn;
    private TextView pageTitle;

    public RFLoader loader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public void showLoader(String message, Boolean isFullScreen){
        loader = new RFLoader(this);
        loader.show(message, isFullScreen);
    }

    public void initActionBar(String title, Boolean requireBackBtn) {
        try {
            pageTitle = findViewById(R.id.page_title);
            pageTitle.setText(title);
            backBtn = findViewById(R.id.backBtn);
            backBtn.setOnClickListener(v -> onBackPressed());

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


    public void replaceFragment(Fragment fragment, int frame){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(frame, fragment);
        fragmentTransaction.commit();
    }

    // used when switching screen without back stack
    // cannot switch between same type of fragment (i.e. cannot have two HomeFragment in transaction manager)
    // use case: switch fragments with bottom nav
    public void switchFragments(Fragment newFragment, int frame) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        boolean fragmentExists = false;

        // Loop through all added fragments
        for (Fragment fragment : fm.getFragments()) {

            // Check if fragment is the exact same obj
            if (fragment.equals(newFragment)) {
                // Show if same fragment
                ft.show(fragment);
                fragmentExists = true;
            }
            else if (fragment.getClass().equals(newFragment.getClass())) {
                // if fragment is same type, remove it so it can be replaced
                ft.remove(fragment);
            } else {
                // Hide all other fragments
                ft.hide(fragment);
            }
        }

        // Add the fragment only if it doesn't exist
        if (!fragmentExists) {
            ft.add(frame, newFragment);
        }

        ft.commit();
    }

}
