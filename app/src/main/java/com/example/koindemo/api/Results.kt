package com.example.koindemo.api

import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("astronomical_twilight_begin")
    val astronomical_twilight_begin: String,
    @SerializedName("astronomical_twilight_end")
    val astronomical_twilight_end: String,
    @SerializedName("civil_twilight_begin")
    val civil_twilight_begin: String,
    @SerializedName("civil_twilight_end")
    val civil_twilight_end: String,
    val day_length: String,
    @SerializedName("nautical_twilight_begin")
    val nautical_twilight_begin: String,
    @SerializedName("nautical_twilight_end")
    val nautical_twilight_end: String,
    @SerializedName("solar_noon")
    val solar_noon: String,
    @SerializedName("sunrise")
    val sunrise: String,
    @SerializedName("sunset")
    val sunset: String
)