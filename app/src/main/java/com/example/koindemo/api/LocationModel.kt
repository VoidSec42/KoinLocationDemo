package com.example.koindemo.api

data class LocationModel(
    val latitude : String,
    val longitude : String
)

/**
 * Check if the location is valid ...
 * ...the latitude is not empty
 * ...the longitude is not empty
 */

fun validateLocationModel(
    latitude: String,
    longitude: String
    ): Boolean {

    if (latitude.isEmpty() || longitude.isEmpty()) {
        return false
    }

    return true
}
