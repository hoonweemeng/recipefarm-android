package com.app.recipefarm.model.response.generic;

import com.app.recipefarm.model.base.ValidationModel;

import java.util.List;

public class RFResponse {
    public boolean success;
    public String errorMessage;
    public List<ValidationModel> validationErrors;
}
