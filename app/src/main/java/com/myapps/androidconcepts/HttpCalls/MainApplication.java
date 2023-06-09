package com.myapps.androidconcepts.HttpCalls;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.myapps.androidconcepts.z_kotlin.http_calls.KotlinRestClient;

public class MainApplication extends Application {
    private static RestClient restClient;
    private static PostRestClient postRestClient;
    private static RecylrRestClient recylrRestClient;
    private static ECommerceRestClient eCommerceRestClient;
    private static AirlinesRestClient airlinesRestClient;
    private static PaginationRestClient paginationRestClient;
    private static WeatherRestClient weatherRestClient;
    private static PracticeRestClient practiceRestClient;
    private static FcmRestClient fcmRestClient;
    private static KotlinRestClient kotlinRestClient;

    public static PostRestClient getPostRestClient() {

        return postRestClient;
    }

    public static RecylrRestClient getRecylrRestClient() {

        return recylrRestClient;
    }

    public static RestClient getRestClient() {

        return restClient;
    }

    public static ECommerceRestClient geteCommerceRestClient() {

        return eCommerceRestClient;
    }

    public static AirlinesRestClient getAirlinesRestClient() {

        return airlinesRestClient;
    }

    public static PaginationRestClient getPaginationRestClient() {

        return paginationRestClient;
    }

    public static WeatherRestClient getWeatherRestClient() {

        return weatherRestClient;
    }

    public static PracticeRestClient getPracticeRestClient() {

        return practiceRestClient;
    }

    public static FcmRestClient getFcmRestClient() {

        return fcmRestClient;
    }

    public static KotlinRestClient getKotlinRestClient() {

        return kotlinRestClient;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        restClient = new RestClient();
        postRestClient = new PostRestClient();
        recylrRestClient = new RecylrRestClient();
        eCommerceRestClient = new ECommerceRestClient();
        airlinesRestClient = new AirlinesRestClient();
        paginationRestClient = new PaginationRestClient();
        weatherRestClient = new WeatherRestClient();
        practiceRestClient = new PracticeRestClient();
        fcmRestClient = new FcmRestClient();
        kotlinRestClient = new KotlinRestClient();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}