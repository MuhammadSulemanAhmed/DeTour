package com.example.suleman_pc.detour.Common1;

import com.example.suleman_pc.detour.Model.OpenWeatherMap;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by suleman-pc on 2/1/2018.
 */
public interface Api_Interface {
    @GET("http://api.openweathermap.org/data/2.5/weather?q=Lahore&appid=7bcaa468ccbfa026448e1402d6b11fd6")
    Call<OpenWeatherMap> getGithubGist();
}