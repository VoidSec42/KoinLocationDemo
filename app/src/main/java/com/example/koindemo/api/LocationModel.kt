package com.example.koindemo.api

data class LocationModel(
    val latitude : String,
    val longitude : String
)

/**
 * Check if the location is valid ...
 * ...the latitude is not empty
 * ...the longitude is not empty
 * ...the latitude is between -90 and 90
 * ...the longitude is between -180 and 180
 */

fun validateLocationModel(
    latitude: String,
    longitude: String
    ): Boolean {

    if (latitude.isEmpty() || longitude.isEmpty()) {
        return false
    }

    if ((latitude.toDouble() !in -90.0..90.0) || (longitude.toDouble() !in -180.0..180.0)) {
        return false
    }
    return true
}
