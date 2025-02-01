package com.app.recipefarm.utility;

import static com.app.recipefarm.utility.RFFunctions.isNullOrBlank;

import com.app.recipefarm.RFDataManager;
import com.app.recipefarm.models.base.ValidationModel;

import java.util.regex.Pattern;

public class ValidationMethods {

    //Validation

    // for recipe form
    public static ValidationModel validateInstructions(String value) {
        if (isNullOrBlank(value)) {
            return new ValidationModel("ingredient", false,"Ingredient field is empty.");
        }
        return new ValidationModel("ingredient", true,null);
    }

    public static ValidationModel validateIngredients(String value) {
        if (isNullOrBlank(value)) {
            return new ValidationModel("ingredient", false,"Ingredient field is empty.");
        }
        return new ValidationModel("ingredient", true,null);
    }
    
    public static ValidationModel validateTitle(String value) {
        String fieldTitle = "title";
        if (isNullOrBlank(value)) {
            return new ValidationModel(fieldTitle, false,"Title is required.");
        }
        return new ValidationModel(fieldTitle, true,null);
    }

    public static ValidationModel validateDescription(String value) {
        String fieldTitle = "description";
        if (isNullOrBlank(value)) {
            return new ValidationModel(fieldTitle, false,"Description is required.");
        }
        return new ValidationModel(fieldTitle, true,null);
    }

    public static ValidationModel validateDuration(String value) {
        String fieldTitle = "duration";
        if (isNullOrBlank(value)) {
            return new ValidationModel(fieldTitle, false,"Duration is required.");
        }

        try {
            Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            return new ValidationModel(fieldTitle, false,"Duration must be an integer.");
        }

        return new ValidationModel(fieldTitle, true,null);
    }

    public static ValidationModel validateServings(String value) {
        String fieldTitle = "servings";
        if (isNullOrBlank(value)) {
            return new ValidationModel(fieldTitle, false,"Servings is required.");
        }

        try {
            Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            return new ValidationModel(fieldTitle, false,"Servings must be an integer.");
        }

        return new ValidationModel(fieldTitle, true,null);
    }

    public static ValidationModel validateRecipeImage() {
        String fieldTitle = "image";
        if (RFDataManager.shared().recipeFormHelper.recipe.recipeImage == null && RFDataManager.shared().recipeFormHelper.recipeImageURI == null) {
            return new ValidationModel(fieldTitle, false,"Image is required.");
        }
        return new ValidationModel(fieldTitle, true,null);
    }


    // for login and register
    public static ValidationModel validateEmailAddress(String value) {
        String fieldTitle = "email";

        if (isNullOrBlank(value)) {
            return new ValidationModel(fieldTitle, false,"Email Address is required.");
        }

        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        if (!Pattern.compile(emailRegex).matcher(value).matches()){
            return new ValidationModel(fieldTitle, false,"Email Address is invalid.");
        }
        return new ValidationModel(fieldTitle, true,null);
    }

    public static ValidationModel validateUsername(String value) {
        String fieldTitle = "username";

        if (value.isEmpty()){
            return new ValidationModel(fieldTitle, false,"Username is required.");
        }

        if (value.length() >= 50){
            return new ValidationModel(fieldTitle, false,"Username must be lesser than 50 characters long.");
        }

        return new ValidationModel(fieldTitle, true,null);
    }

    public static ValidationModel validatePassword(String pwd, String retypePwd) {
        String fieldTitle = "password";

        if (pwd.isEmpty() || retypePwd.isEmpty()){
            return new ValidationModel(fieldTitle, false,"Password is required.");
        }

        if (pwd.length() < 8){
            return new ValidationModel(fieldTitle, false,"Password must be at least 8 characters long.");
        }

        if (!pwd.equals(retypePwd)){
            return new ValidationModel(fieldTitle, false,"Password is not the same as Retype Password.");
        }

        return new ValidationModel(fieldTitle, true,null);
    }
}
