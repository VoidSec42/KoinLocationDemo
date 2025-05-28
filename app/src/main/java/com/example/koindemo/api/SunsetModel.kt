package com.example.koindemo.api

data class SunsetModel(
    val results: Results,
    val status: String,
    val tzid: String
)