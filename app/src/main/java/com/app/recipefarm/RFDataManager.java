package com.app.recipefarm;

public class RFDataManager {
    private RFDataManager() {}

    // Inner static helper class
    private static class RFDataManagerHelper {
        private static final RFDataManager INSTANCE = new RFDataManager();
    }

    public static RFDataManagerData shared() {
        return RFDataManagerHelper.INSTANCE.rfDataManagerData;
    }
    private RFDataManagerData rfDataManagerData= new RFDataManagerData();

    public static void reset(){
        RFDataManagerHelper.INSTANCE.rfDataManagerData = new RFDataManagerData();
    }

}


