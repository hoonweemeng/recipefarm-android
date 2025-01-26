package com.app.recipefarm.core;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.app.recipefarm.R;

public class RFDialog extends Dialog {

    private String title;
    private String body;
    private String posBtnText;
    private String negBtnText;
    private OnDialogActionListener actionListener;

    // Interface for callbacks
    public interface OnDialogActionListener {
        void onPositiveAction();
        void onNegativeAction();
    }

    public RFDialog(@NonNull Context context, String title, String body, String posBtnText, String negBtnText, OnDialogActionListener listener) {
        super(context);
        this.title = title;
        this.body = body;
        this.posBtnText = posBtnText;
        this.negBtnText = negBtnText;
        this.actionListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog); // Use your custom layout

        // Initialize UI components
        TextView titleText = findViewById(R.id.dialogTitleText);
        TextView bodyText = findViewById(R.id.dialogBodyText);
        Button posBtn = findViewById(R.id.posBtn);
        Button negBtn = findViewById(R.id.negBtn);

        // Set title and button text
        titleText.setText(title);
        bodyText.setText(body);

        if (posBtnText != null){
            posBtn.setText(posBtnText);
            // Set positive button click listener
            posBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (actionListener != null) {
                        actionListener.onPositiveAction();
                    }
                    dismiss(); // Close the dialog
                }
            });
        }
        else {
            posBtn.setVisibility(View.GONE);
        }

        if (negBtnText != null){
            negBtn.setText(negBtnText);
            // Set positive button click listener
            negBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (actionListener != null) {
                        actionListener.onNegativeAction();
                    }
                    dismiss(); // Close the dialog
                }
            });
        }
        else {
            negBtn.setVisibility(View.GONE);
        }

    }

}
