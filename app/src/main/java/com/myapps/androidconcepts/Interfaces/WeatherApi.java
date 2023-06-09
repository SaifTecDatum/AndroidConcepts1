package com.myapps.androidconcepts.Interfaces;

import com.myapps.androidconcepts.Models.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WeatherApi {

    /*Below api query belongs to https://api.weather.gov/gridpoints/TOP/31,80/  */

    @GET("forecast")
    Call<WeatherModel> getModel();

    //here we're not taking ArrayList or List bcoz the api data starting with jsonObject{ } instead of jsonArray[]
}
