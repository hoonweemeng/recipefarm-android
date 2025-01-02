package com.app.recipefarm.core;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.recipefarm.R;

// Core Activity with shared methods and variables
public class RFActivity extends Activity {
    private ImageView backBtn;
    private TextView pageTitle;

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
