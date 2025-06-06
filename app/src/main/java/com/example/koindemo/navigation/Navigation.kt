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
fun Nav() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel()
    NavHost(navController = navController, startDestination = "Start") {

        composable(route = "Hamburg") {
            HamburgLocationPage(viewModel, navController)
        }

        composable(route = "Frankfurt") {
            FrankfurtLocationPage(viewModel, navController)
        }

        composable(route = "Berlin") {
            BerlinLocationPage(viewModel, navController)
        }

        composable(route = "Kiel") {
            KielLocationPage(viewModel, navController)
        }

        composable(route = "Start") {
            StartPage(viewModel, navController)
        }

    }
}

