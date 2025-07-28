package com.example.koindemo

import android.location.Location
import com.example.koindemo.api.LocationModel

interface GeoLocationInterface {
   suspend fun getLocation(): LocationModel?
}