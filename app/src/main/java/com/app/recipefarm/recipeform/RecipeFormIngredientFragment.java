package com.app.recipefarm.recipeform;

import static com.app.recipefarm.utility.RFFunctions.getInvalidEntries;
import static com.app.recipefarm.utility.ValidationMethods.validateIngredients;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.app.recipefarm.R;
import com.app.recipefarm.RFDataManager;
import com.app.recipefarm.core.RFDialog;
import com.app.recipefarm.core.RFFragment;
import com.app.recipefarm.model.base.ValidationModel;
import com.app.recipefarm.recipedetail.RecipeDetailAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecipeFormIngredientFragment extends RFFragment {

    private RecipeDetailAdapter recipeDetailAdapter;
    private RecyclerView recyclerView;
    private Button nextBtn;
    private Button addBtn;
    private EditText ingredientField;
    private ArrayList<String> ingredientList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_recipe_form_ingredient, container, false);

        recyclerView = mainView.findViewById(R.id.recipeFormRV);
        nextBtn = mainView.findViewById(R.id.recipeformIngredientNextBtn);
        addBtn = mainView.findViewById(R.id.recipeformIngredientAddBtn);
        ingredientField = mainView.findViewById(R.id.recipeformIngredientField);

        nextBtn.setOnClickListener(v -> next());
        addBtn.setOnClickListener(v -> onClickAdd());

        ingredientList = RFDataManager.shared().recipeFormHelper.recipe.getIngredients();

        recipeDetailAdapter = new RecipeDetailAdapter(getContext(), ingredientList, id -> {
            // show delete option
        });

        recyclerView.setAdapter(recipeDetailAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return mainView;
    }

    private void onClickAdd() {
        String value = String.valueOf(ingredientField.getText());
        List<ValidationModel> validationList = new ArrayList<>();
        validationList.add(validateIngredients(value));
        List<ValidationModel> invalidEntries = getInvalidEntries(validationList);

        if (!invalidEntries.isEmpty()){
            RFDialog dialog = new RFDialog(getContext(), "Error", invalidEntries.get(0).message, null, "Close", null);
            dialog.show();
            return;
        }

        // save to recipeFormHelper
        ingredientList.add(value);
        RFDataManager.shared().recipeFormHelper.recipe.setIngredients(ingredientList);

        // update data in recyclerview
        recipeDetailAdapter.notifyItemInserted(ingredientList.size() - 1);

        //clear text in ingredient field
        ingredientField.setText("");
    }

    private void next() {
        // make sure ingredient list is not empty
        if (ingredientList.isEmpty()){
            RFDialog dialog = new RFDialog(getContext(), "Error", "Ingredients are required.", null, "Close", null);
            dialog.show();
            return;
        }

        navigateToInstructionPage();
    }


    private void navigateToInstructionPage() {
        RecipeFormInstructionFragment instructionFragment = new RecipeFormInstructionFragment();
        navigateToAnotherFragment(instructionFragment, R.id.recipeFormFrame);
    }


}