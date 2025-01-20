package com.app.recipefarm;

import com.app.recipefarm.models.base.User;

public class RFDataManager {
    private RFDataManager() {}

    // Inner static helper class
    private static class RFDataManagerHelper {
        private static final RFDataManager INSTANCE = new RFDataManager();
    }

    public static RFDataManager shared() {
        return RFDataManagerHelper.INSTANCE;
    }

    public MainActivityHelper mainActivityHelper = new MainActivityHelper();

    public User user = null;


}


