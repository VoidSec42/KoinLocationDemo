package com.example.koindemo

import android.Manifest
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.annotation.RequiresPermission
import com.example.koindemo.api.Api
import com.example.koindemo.api.LocationModel
import com.example.koindemo.api.SunsetModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await
import retrofit2.Response

class MainRepository(private val api: Api, private val context: Context) :
    MainRepositoryInterface {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var mLocation: Location? = null

    override suspend fun getSunsetData(longitude: String, latitude: String): Response<SunsetModel> {
        return api.callSunsetApi(longitude, latitude)
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override suspend fun getLastLocation(): LocationModel? {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        return try {
            val location: Location? = fusedLocationProviderClient.lastLocation.await() // This will suspend until the task is complete

            if (location != null) {
                Log.i("LocationRepository", "Location is " + location.latitude + " " + location.longitude)
                LocationModel(
                    latitude = location.latitude.toString(),
                    longitude = location.longitude.toString()
                )
            } else {
                Log.e("LocationRepository", "FusedLocationProviderClient.lastLocation returned null")
                null // Return null if location is null
            }
        } catch (e: Exception) {
            // This will catch exceptions if the Task fails (e.g., security exception if permissions are missing at runtime)
            Log.e("LocationRepository", "Error getting last location", e)
            // You might want to throw a custom exception or return null based on your error handling strategy
            null
        }
    }

}



