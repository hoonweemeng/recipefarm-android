package com.app.recipefarm;

public class RFDataManager {
    private RFDataManager() {}

    // Inner static helper class
    private static class RFDataManagerHelper {
        private static final RFDataManager INSTANCE = new RFDataManager();
    }

    public static RFDataManager shared() {
        return RFDataManagerHelper.INSTANCE;
    }




}


