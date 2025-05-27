package com.example.koindemo

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationProvider
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
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



