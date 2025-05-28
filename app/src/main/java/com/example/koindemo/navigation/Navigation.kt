package com.example.koindemo.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.koindemo.MainViewModel
import com.example.koindemo.pages.StartPage


@Composable
fun Navigation(navController: NavHostController) {
    val navController = rememberNavController()
    val myViewModel: MainViewModel = viewModel()
    NavHost(navController = navController, startDestination = Screen.StartScreen.route) {
        composable(route = Screen.HamburgLocationScreen.route) {
            StartPage(myViewModel, navController)
        }
    }

}

