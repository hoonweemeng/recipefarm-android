package com.app.recipefarm.utility;

import com.app.recipefarm.models.base.ValidationModel;

import java.util.List;
import java.util.stream.Collectors;

public class RFFunctions {

    public static List<ValidationModel> invalidEntries(List<ValidationModel> validationList){
        validationList.stream()
                .filter(validation -> !validation.isValid)
                .collect(Collectors.toList());
        return validationList;
    }


}
