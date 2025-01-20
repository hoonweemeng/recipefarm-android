package com.app.recipefarm.utility;

import static com.app.recipefarm.utility.Constants.USERID;

import android.content.Context;

import com.app.recipefarm.RFDataManager;
import com.app.recipefarm.models.base.ValidationModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RFFunctions {

    public static List<ValidationModel> invalidEntries(List<ValidationModel> validationList){
        validationList.stream()
                .filter(validation -> !validation.isValid)
                .collect(Collectors.toList());
        return validationList;
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
