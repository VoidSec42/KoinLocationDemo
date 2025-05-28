package com.example.koindemo.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.koindemo.MainViewModel
import com.example.koindemo.navigation.Screen

@Composable
fun StartPage(viewModel: MainViewModel, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Row to open HamburgLocationPage
        Spacer(modifier = Modifier.height(64.dp))
        HeadlineAndText()
    }
}

@Composable
fun HeadlineAndText(
) {
    Row {
        Text(
            text = "Sunset Times",
            fontSize = 22.sp,
            textAlign = TextAlign.Center
        )
    }
    Spacer(modifier = Modifier.height(32.dp))
    Row {
        Text(
            text = "For now this page shows hardcoded buttons to some number of locations. When clicked the opened page should show sunset and sunrise data for this location. Later this will be changed to dynamic locations.",
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
    Spacer(modifier = Modifier.height(32.dp))
    Buttons()
}

@Composable
fun Buttons() {
    val navController = rememberNavController()
    Row {
        Button(onClick = { navController.navigate(Screen.HamburgLocationScreen.route)
        }) {
            Text("Go to Hamburg Page")
        }
    }
    Spacer(modifier = Modifier.height(32.dp))
    Row {
        Button(onClick = { /*navController.navigate.*/

        }) {
            Text("Go to Berlin Page")
        }
    }
    Spacer(modifier = Modifier.height(32.dp))
    Row {
        Button(onClick = { /*navController.navigate.*/

        }) {
            Text("Go to Frankfurt Page")
        }
    }
    Spacer(modifier = Modifier.height(32.dp))
    Row {
        Button(onClick = { /*navController.navigate.*/

        }) {
            Text("Go to Kiel Page")
        }
    }
}



