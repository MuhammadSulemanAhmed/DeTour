package com.example.suleman_pc.detour;


import com.example.suleman_pc.detour.NearbyModel.Results;
import com.example.suleman_pc.detour.Remote.IGoogleAPIService;
import com.example.suleman_pc.detour.Remote.RetrofitClient;

/**
 * Created by suleman-pc on 3/30/2018.
 */

public class Common {

    public static Results currentResult;
    private static final String GOOGLE_API_URL="https://maps.googleapis.com/";
    public static IGoogleAPIService getGoogleAPIService(){

        return RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService.class);
    }
}
