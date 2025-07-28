package com.example.koindemo

import android.Manifest
import android.location.Location
import android.util.Log
import android.util.Log.e
import androidx.annotation.RequiresPermission
import com.example.koindemo.api.Api
import com.example.koindemo.api.LocationModel
import com.example.koindemo.api.SunsetModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await
import retrofit2.Response

class MainRepository(
    private val api: Api,
    private val geoLocation: GeoLocationInterface,
) : MainRepositoryInterface {

    override suspend fun getSunsetData(longitude: String, latitude: String): Response<SunsetModel> {
        return api.callSunsetApi(longitude, latitude)
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override suspend fun getLastLocation(): LocationModel? {
        val locationModel: LocationModel? = geoLocation.getLocation()
        return locationModel
    }
}
