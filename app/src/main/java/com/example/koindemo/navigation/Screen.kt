package com.example.koindemo.navigation

sealed class Screen(val route: String) {
    object StartScreen: Screen("start_screen")
    object SunsetScreen: Screen("sunset_screen")
    object HamburgLocationScreen: Screen("hamburg_location_screen")
    object BerlinLocationScreen: Screen("berlin_location_screen")
    object FrankfurtLocationScreen: Screen("frankfurt_location_screen")
    object KielLocationScreen: Screen("kiel_location_screen")
}