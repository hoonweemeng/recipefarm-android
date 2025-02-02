package com.app.recipefarm.utility;

import static com.app.recipefarm.utility.Constants.GENERIC_ERROR_MSG;
import static com.app.recipefarm.utility.Constants.PROFILE_IMAGES;
import static com.app.recipefarm.utility.Constants.USERID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.app.recipefarm.MainActivity;
import com.app.recipefarm.RFDataManager;
import com.app.recipefarm.core.RFDialog;
import com.app.recipefarm.model.base.ValidationModel;
import com.app.recipefarm.model.response.generic.RFResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RFFunctions {

    public static ArrayList<String> jsonToList(String jsonString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(jsonString, listType);
    }

    public static String listToJson(ArrayList<String> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }


    public static void logout(Activity activity) {
        // clear all data
        RFDataManager.reset();
        SharedPrefsManager.shared(activity).clearAll();

        Intent intent = new Intent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    public static String getProfileImagePath(String fileName) {
        return PROFILE_IMAGES + "/" + fileName;
    }

    public static Boolean isNullOrBlank(String value) {
        return (value == null || value.isBlank());
    }

    public static Boolean isResponseSuccessful(RFResponse response) {
        return (response != null && response.success);
    }

    public static void responseErrorHandler(Context context, RFResponse response) {
        if (response == null) {
            RFDialog dialog = new RFDialog(context, "Error", GENERIC_ERROR_MSG, null, "Close", null);
            dialog.show();
            return;
        }
        if (response.validationErrors != null && !response.validationErrors.isEmpty()){
            RFDialog dialog = new RFDialog(context, "Error", response.validationErrors.get(0).message, null, "Close", null);
            dialog.show();
        }
        else if (!isNullOrBlank(response.errorMessage)) {
            RFDialog dialog = new RFDialog(context, "Error", response.errorMessage, null, "Close", null);
            dialog.show();
        }
        else {
            RFDialog dialog = new RFDialog(context, "Error", GENERIC_ERROR_MSG, null, "Close", null);
            dialog.show();
        }
    }

    public static List<ValidationModel> getInvalidEntries(List<ValidationModel> validationList){
        return validationList.stream()
                .filter(validation -> !validation.isValid)
                .collect(Collectors.toList());
    }

    public static Map<String,String> getHeaders(Context context) {
        String userId =  SharedPrefsManager.shared(context).getData(USERID, String.class);
        Map<String,String> map = new HashMap<>();
        if (userId != null || !userId.isBlank()) {
            map.put(USERID,userId);
        }
        return map;
    }


}
