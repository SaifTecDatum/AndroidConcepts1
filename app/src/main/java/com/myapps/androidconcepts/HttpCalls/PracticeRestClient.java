package com.myapps.androidconcepts.HttpCalls;

import com.myapps.androidconcepts.BuildConfig;
import com.myapps.androidconcepts.Helpers.Constants;
import com.myapps.androidconcepts.Interfaces.MyApi;
import com.myapps.androidconcepts.Interfaces.PracticeApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PracticeRestClient {
    private PracticeApi practiceApi;
    //private MyApi myApi;

    public PracticeRestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.weatherBaseUrl)
                //.baseUrl(Constants.currencyBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(buildOkHttpClient())
                .build();

        practiceApi = retrofit.create(PracticeApi.class);
        //myApi = retrofit.create(MyApi.class);
    }

    public PracticeApi getPracticeApi() {

        return practiceApi;
    }

    /*public MyApi getMyApi() {return myApi;}*/

    private OkHttpClient buildOkHttpClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .connectTimeout(90, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .connectionPool(new ConnectionPool(0, 1, TimeUnit.NANOSECONDS));

        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request loginRequest = originalRequest.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .header("Accept-Language", "en-gb")
                        .method(originalRequest.method(), originalRequest.body())
                        .build();

                return chain.proceed(loginRequest);
            }
        });

        builder.addInterceptor(loggingInterceptor);
        builder.readTimeout(1, TimeUnit.MINUTES);
        builder.connectTimeout(1, TimeUnit.MINUTES);

        return builder.build();
    }
}