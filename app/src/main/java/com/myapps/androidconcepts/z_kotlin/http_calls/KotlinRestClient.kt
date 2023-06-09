package com.myapps.androidconcepts.z_kotlin.http_calls

import com.myapps.androidconcepts.BuildConfig
import com.myapps.androidconcepts.z_kotlin.helpers.KotlinConstants.Companion.baseUrl
import com.myapps.androidconcepts.z_kotlin.interfaces.KotlinApi
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class KotlinRestClient {
    private var kotlinApi: KotlinApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(buildOkHttpClient())
            .build()

        kotlinApi = retrofit.create(KotlinApi::class.java)
    }

    fun getMyApi(): KotlinApi {
        return kotlinApi
    }

    private fun buildOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)

        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))

        builder.addInterceptor(Interceptor { chain ->

            val originalRequest: Request = chain.request()
            val loginRequest: Request = originalRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Accept-Language", "en-GB")
                .method(originalRequest.method, originalRequest.body)
                .build()

            chain.proceed(loginRequest)
        })

        builder.addInterceptor(loggingInterceptor)
        builder.readTimeout(1, TimeUnit.MINUTES)
        builder.connectTimeout(1, TimeUnit.MINUTES)

        return builder.build()
    }
}