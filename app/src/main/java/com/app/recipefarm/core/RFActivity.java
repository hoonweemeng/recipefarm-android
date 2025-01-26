package com.app.recipefarm.core;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    public void showLoader(String message){
        loader = new RFLoader(this);
        loader.show(message);
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

}
