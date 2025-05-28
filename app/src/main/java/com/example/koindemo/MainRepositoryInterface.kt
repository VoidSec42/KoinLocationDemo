package com.example.koindemo

import com.example.koindemo.api.LocationModel
import com.example.koindemo.api.SunsetModel
import retrofit2.Response

interface MainRepositoryInterface {
    suspend fun getSunsetData(longitude: String, latitude: String): Response<SunsetModel>
    suspend fun getLastLocation(): LocationModel?
}