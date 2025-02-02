package com.app.recipefarm.core;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import com.app.recipefarm.R;

public class RFLoader {

    private View view;
    private final Dialog dialog;
    private ProgressBar progressBar;

    // Constructor
    public RFLoader(Context context) {
        dialog = new Dialog(context);
        initLoader();
    }

    private void initLoader() {
        // Inflate the custom loader layout
        view = LayoutInflater.from(dialog.getContext()).inflate(R.layout.layout_rf_loader, null);

        // Configure the dialog
        dialog.setContentView(view);
        dialog.setCancelable(false); // Prevent dismissal by clicking outside
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // Get the ProgressBar reference
        progressBar = view.findViewById(R.id.progressBarLoader);

        // set default color
        setColorFromRes(R.color.cadet_blue);
    }

    // Show the loader
    public void show(String message, boolean isFullScreen) {
        // Set full-screen if enabled
        if (isFullScreen) {
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        } else {
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }
        // Set the message if provided
        if (message != null) {
            TextView tvMessage = dialog.findViewById(R.id.tvLoaderMessage);
            tvMessage.setText(message);
            tvMessage.setVisibility(View.VISIBLE);
        }
        dialog.show();
    }

    // Set the loader color
    public void setColorFromRes(@ColorRes int colorResId) {
        if (progressBar != null) {
            int color = ContextCompat.getColor(dialog.getContext(), colorResId);
            progressBar.setIndeterminateTintList(android.content.res.ColorStateList.valueOf(color));
        }
    }

    // Hide the loader
    public void hide() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
