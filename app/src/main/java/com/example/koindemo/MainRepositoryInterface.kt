package com.example.koindemo

import android.content.Context
import android.location.Location
import retrofit2.Response

interface MainRepositoryInterface {
    suspend fun getSunsetData(longitude: String, latitude: String): Response<SunsetModel>
    suspend fun getLastLocation(): LocationModel?
}