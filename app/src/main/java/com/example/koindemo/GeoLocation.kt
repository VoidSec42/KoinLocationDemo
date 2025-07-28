package com.example.koindemo

import android.Manifest
import android.location.Location
import androidx.annotation.RequiresPermission
import com.example.koindemo.api.LocationModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

class GeoLocation(private val contextProvider: ContextProvider): GeoLocationInterface {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])

    override suspend fun getLocation(): LocationModel? {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(contextProvider.context)

        val location: Location? = fusedLocationProviderClient.lastLocation.await()
        var locationModel : LocationModel? = null

        if (location != null) {
            locationModel = LocationModel(
                latitude = location.latitude.toString(),
                longitude = location.longitude.toString()
            )
        }
        return locationModel
    }
}