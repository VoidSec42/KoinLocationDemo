package com.example.koindemo.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.koindemo.MainViewModel
import com.example.koindemo.pages.BerlinLocationPage
import com.example.koindemo.pages.FrankfurtLocationPage
import com.example.koindemo.pages.HamburgLocationPage
import com.example.koindemo.pages.KielLocationPage
import com.example.koindemo.pages.StartPage
import com.example.koindemo.pages.SunsetPage


@Composable
fun Navigation(navController: NavHostController) {
    val navController = rememberNavController()
    val myViewModel: MainViewModel = viewModel()
    NavHost(navController = navController, startDestination = Screen.StartScreen.route) {
        composable(route = Screen.StartScreen.route) {
            StartPage(myViewModel, navController)
        }
        composable(route = Screen.HamburgLocationScreen.route) {
            HamburgLocationPage(myViewModel, navController)
        }
        composable(route = Screen.BerlinLocationScreen.route) {
            BerlinLocationPage(myViewModel, navController)
        }
        composable(route = Screen.FrankfurtLocationScreen.route) {
            FrankfurtLocationPage(myViewModel, navController)
        }
        composable(route = Screen.KielLocationScreen.route) {
            KielLocationPage(myViewModel, navController)
        }
        composable(route = Screen.SunsetScreen.route) {
            SunsetPage(myViewModel, navController)
        }
    }
}

