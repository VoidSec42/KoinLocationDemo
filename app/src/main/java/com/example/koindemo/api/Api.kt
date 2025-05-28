package com.example.koindemo.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("/json")
    suspend fun callSunsetApi(
        @Query("lat") latitude : String,
        @Query("lng") longitude : String
    ): Response<SunsetModel>

}