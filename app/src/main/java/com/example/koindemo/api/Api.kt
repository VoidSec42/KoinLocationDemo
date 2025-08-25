package com.example.koindemo.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("/json")
    suspend fun callSunsetApi(
        @Query("lng") longitude : String,
        @Query("lat") latitude : String,
        // @Query("formatted") formatted : Int = 0,
        // @Query(value = "tzid") timezone : String = "Europe/Berlin"
    ): Response<SunsetModel>

}