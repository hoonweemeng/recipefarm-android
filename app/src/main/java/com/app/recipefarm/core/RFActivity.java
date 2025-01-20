package com.app.recipefarm.core;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.app.recipefarm.R;
import com.app.recipefarm.utility.RFLoader;

// Core Activity with shared methods and variables
public class RFActivity extends AppCompatActivity {
    private ImageView backBtn;
    private TextView pageTitle;

    public RFLoader loader;

    public void showLoader(String message){
        loader = new RFLoader(this);
        loader.show(message);
    }

    public void initActionBar(String title) {
        try {
            pageTitle = findViewById(R.id.page_title);
            pageTitle.setText(title);
            backBtn = findViewById(R.id.backBtn);
            backBtn.setOnClickListener(v -> onBackPressed());
        }
        catch (Exception exception){
            // Action Bar not found
        }
    }

}
