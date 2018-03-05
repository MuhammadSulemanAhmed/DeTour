package com.example.suleman_pc.detour.Common;

/**
 * Created by suleman-pc on 3/4/2018.
 */

public class ShareData {
    private static ShareData instance=null;
    private ShareData() {};
    public int current_TripId;
    public static ShareData getInstance() {
        if (instance == null) {
            instance = new ShareData();
        }
        return(instance);
    }
}
