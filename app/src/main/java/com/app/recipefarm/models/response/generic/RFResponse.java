package com.app.recipefarm.models.response.generic;

import com.app.recipefarm.models.base.ValidationModel;

import java.util.List;

public class RFResponse {
    public boolean success;
    public String errorMessage;
    public List<ValidationModel> validationErrors;
}
