package com.myapps.androidconcepts.z_kotlin.interfaces

import com.myapps.androidconcepts.z_kotlin.models.KotlinModel
import retrofit2.Call
import retrofit2.http.GET

interface KotlinApi {

    @GET("posts/")
    fun getData() : Call<List<KotlinModel>>

}