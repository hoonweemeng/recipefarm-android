package com.app.recipefarm.utility;

import com.app.recipefarm.RFDataManager;
import com.app.recipefarm.models.base.ValidationModel;

import java.util.regex.Pattern;

public class ValidationMethods {

    //Validation

    // for recipe form
    public static ValidationModel validateTitle(String value) {
        if (value == null || value.isEmpty()) {
            return new ValidationModel("title", false,"Title is required.");
        }
        return new ValidationModel("title", true,null);
    }

    public static ValidationModel validateDescription(String value) {
        if (value == null || value.isEmpty()) {
            return new ValidationModel("description", false,"Title is required.");
        }
        return new ValidationModel("description", true,null);
    }

    public static ValidationModel validateDuration(String value) {
        if (value == null || value.isEmpty()) {
            return new ValidationModel("duration", false,"Duration is required.");
        }

        try {
            Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            return new ValidationModel("duration", false,"Duration must be an integer.");
        }

        return new ValidationModel("duration", true,null);
    }

    public static ValidationModel validateServings(String value) {
        if (value == null || value.isEmpty()) {
            return new ValidationModel("servings", false,"Servings is required.");
        }

        try {
            Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            return new ValidationModel("servings", false,"Servings must be an integer.");
        }

        return new ValidationModel("servings", true,null);
    }

    public static ValidationModel validateRecipeImage() {
        if (RFDataManager.shared().recipeFormHelper.recipe.recipeImage == null && RFDataManager.shared().recipeFormHelper.recipeImageURI == null) {
            return new ValidationModel("image", false,"Image is required.");
        }
        return new ValidationModel("image", true,null);
    }


    // for login and register
    public static ValidationModel validateEmailAddress(String value) {

        if (value == null || value.isEmpty()) {
            return new ValidationModel("email", false,"Email Address is required.");
        }

        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        if (!Pattern.compile(emailRegex).matcher(value).matches()){
            return new ValidationModel("email", false,"Email Address is invalid.");
        }
        return new ValidationModel("email", true,null);
    }

    public static ValidationModel validateUsername(String value) {

        if (value.isEmpty()){
            return new ValidationModel("username", false,"Username is required.");
        }

        if (value.length() > 50){
            return new ValidationModel("password", false,"Username must be at lesser than 50 characters long.");
        }

        return new ValidationModel("username", true,null);
    }

    public static ValidationModel validatePassword(String pwd, String retypePwd) {

        if (pwd.isEmpty() || retypePwd.isEmpty()){
            return new ValidationModel("password", false,"Password is required.");
        }

        if (pwd.length() < 8){
            return new ValidationModel("password", false,"Password must be at least 8 characters long.");
        }

        if (!pwd.equals(retypePwd)){
            return new ValidationModel("password", false,"Password is not the same as Retype Password.");
        }

        return new ValidationModel("password", true,null);
    }
}
