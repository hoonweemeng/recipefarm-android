package com.app.recipefarm.model.base;

public class ValidationModel {
    public String fieldTitle;
    public boolean isValid;
    public String message;

    public ValidationModel(String fieldTitle, boolean isValid, String message) {
        this.fieldTitle = fieldTitle;
        this.isValid = isValid;
        this.message = message;
    }
}
