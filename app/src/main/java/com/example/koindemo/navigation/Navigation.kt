package com.example.koindemo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.koindemo.pages.BerlinLocationPage
import com.example.koindemo.pages.FrankfurtLocationPage
import com.example.koindemo.pages.HamburgLocationPage
import com.example.koindemo.pages.KielLocationPage
import com.example.koindemo.pages.StartPage
import com.example.koindemo.pages.SunsetPage


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.StartScreen.route) {
        composable(route = Screen.StartScreen.route) {
            StartPage(
                onNavigateToHamburg = {
                    navController.navigate(Screen.HamburgLocationScreen.route)
                },
                onNavigateToBerlin = {
                    navController.navigate(Screen.BerlinLocationScreen.route)
                },
                onNavigateToFrankfurt = {
                    navController.navigate(Screen.FrankfurtLocationScreen.route)
                },
                onNavigateToKiel = {
                    navController.navigate(Screen.KielLocationScreen.route)
                },
                onNavigateToSunset = {
                    navController.navigate(Screen.SunsetScreen.route)
                }
            )
        }
        composable(route = Screen.HamburgLocationScreen.route) {
            HamburgLocationPage(onNavigateToStart = {
                navController.navigate(Screen.StartScreen.route)
            })
        }
        composable(route = Screen.BerlinLocationScreen.route) {
            BerlinLocationPage(onNavigateToStart = {
                navController.navigate(Screen.StartScreen.route)
            })
        }
        composable(route = Screen.FrankfurtLocationScreen.route) {
            FrankfurtLocationPage(onNavigateToStart = {
                navController.navigate(Screen.StartScreen.route)
            })
        }
        composable(route = Screen.KielLocationScreen.route) {
            KielLocationPage(onNavigateToStart = {
                navController.navigate(Screen.StartScreen.route)
            })
        }
        composable(route = Screen.SunsetScreen.route) {
            SunsetPage(onNavigateToStart = {
                navController.navigate(Screen.StartScreen.route)
            })
        }
    }
}

