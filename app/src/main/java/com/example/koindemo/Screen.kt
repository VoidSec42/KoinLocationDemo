package com.example.koindemo

sealed class Screen(val route: String) {
    object SunsetScreen: Screen("sunset_screen")
    object HamburgScreen: Screen("hamburg_screen")
}