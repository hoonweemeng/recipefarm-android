package com.app.recipefarm.recipeform;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.recipefarm.R;
import com.app.recipefarm.RFDataManager;
import com.app.recipefarm.core.RFActivity;

public class RecipeFormActivity extends RFActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe_form);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        replaceFragment(new RecipeFormMainFragment(), R.id.recipeFormFrame);

        String title = "Create Recipe";
        if (RFDataManager.shared().recipeFormHelper.existingRecipe != null) {
            title = "Update Recipe";
            RFDataManager.shared().recipeFormHelper.recipe = RFDataManager.shared().recipeFormHelper.existingRecipe;
        }

        initActionBar(title, true);

    }

    @Override
    protected void onDestroy() {
        RFDataManager.shared().recipeFormHelper = new RecipeFormHelper();
        super.onDestroy();
    }
}