package com.myapps.androidconcepts.Interfaces;

import com.myapps.androidconcepts.Models.CurrencyModel;
import com.myapps.androidconcepts.Models.PracticeModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PracticeApi {

    /*Below fake api belongs to https://api.weather.gov/gridpoints/TOP/31,80/  */

    @GET("forecast")
    Call<PracticeModel> getPracticeModel();
}



