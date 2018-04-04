package com.example.suleman_pc.detour.Remote;


import com.example.suleman_pc.detour.NearbyModel.Myplaces;
import com.example.suleman_pc.detour.NearbyModel.PlaceDetail;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by suleman-pc on 3/30/2018.
 */

public interface IGoogleAPIService {
    @GET
    Call<Myplaces> getNearByPlaces(@Url String url);
    @GET
    Call<PlaceDetail> getDetailPlaces(@Url String url);
}
