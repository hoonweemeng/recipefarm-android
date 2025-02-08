package com.app.recipefarm.recipedetail;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.recipefarm.R;
import com.app.recipefarm.core.RFActivity;

public class RecipeDetailActivity extends RFActivity {

    private ImageView imageView;
    private TextView titleTv;
    private TextView descriptionTv;

    private RecyclerView ingredientRV;
    private RecyclerView instructionRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe_detail);


    }
}