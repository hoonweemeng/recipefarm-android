package com.app.recipefarm.utility;

import com.app.recipefarm.models.base.ValidationModel;

import java.util.regex.Pattern;

public class ValidationMethods {

    //Validation



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
