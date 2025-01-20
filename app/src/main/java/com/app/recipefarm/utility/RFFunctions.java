package com.app.recipefarm.utility;

import static com.app.recipefarm.utility.Constants.USERID;

import android.content.Context;

import com.app.recipefarm.RFDataManager;
import com.app.recipefarm.core.RFDialog;
import com.app.recipefarm.models.base.ValidationModel;
import com.app.recipefarm.models.response.generic.RFResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RFFunctions {

    public static Boolean isNullOrBlank(String value) {
        return (value == null || value.isBlank());
    }

    public static void responseErrorHandler(Context context, RFResponse response) {
        if (response.validationErrors != null && !response.validationErrors.isEmpty()){
            RFDialog dialog = new RFDialog(context, "Error", response.validationErrors.get(0).message, null, "Close", null);
            dialog.show();
        }
        else if (!isNullOrBlank(response.errorMessage)) {
            RFDialog dialog = new RFDialog(context, "Error", response.errorMessage, null, "Close", null);
            dialog.show();
        }
        else {
            RFDialog dialog = new RFDialog(context, "Error", "Error 500", null, "Close", null);
            dialog.show();
        }
    }

    public static List<ValidationModel> getInvalidEntries(List<ValidationModel> validationList){
        return validationList.stream()
                .filter(validation -> !validation.isValid)
                .collect(Collectors.toList());
    }

    public static Map<String,String> getHeaders() {
        String userId =  RFDataManager.shared().user.userId;
        Map<String,String> map = new HashMap<>();
        if (userId != null || !userId.isBlank()) {
            map.put(USERID,userId);
        }
        return map;
    }

    public static Map<String,String> getHeadersForFirstCall(Context context) {
        String userId =  SharedPrefsManager.shared(context).getData(USERID, String.class);
        Map<String,String> map = new HashMap<>();
        if (userId != null || !userId.isBlank()) {
            map.put(USERID,userId);
        }
        return map;
    }


}
