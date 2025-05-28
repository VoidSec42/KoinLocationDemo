package com.example.koindemo

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.viewmodel.ext.android.getViewModel


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

