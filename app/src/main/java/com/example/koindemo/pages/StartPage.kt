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
import androidx.navigation.compose.rememberNavController
import com.example.koindemo.navigation.Screen

@Composable
fun StartPage(
    onNavigateToHamburg: () -> Unit,
    onNavigateToBerlin: () -> Unit,
    onNavigateToFrankfurt: () -> Unit,
    onNavigateToKiel: () -> Unit,
    onNavigateToSunset: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

//        val navController = rememberNavController()

        // Row to open HamburgLocationPage
        Spacer(modifier = Modifier.height(64.dp))
        HeadlineAndText(
            onNavigateToHamburg,
            onNavigateToBerlin,
            onNavigateToFrankfurt,
            onNavigateToKiel,
            onNavigateToSunset
        )
//        Navigation(navController)
    }
}

@Composable
fun HeadlineAndText(
    onNavigateToHamburg: () -> Unit,
    onNavigateToBerlin: () -> Unit,
    onNavigateToFrankfurt: () -> Unit,
    onNavigateToKiel: () -> Unit,
    onNavigateToSunset: () -> Unit
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
    Buttons(
        onNavigateToHamburg,
        onNavigateToBerlin,
        onNavigateToFrankfurt,
        onNavigateToKiel,
        onNavigateToSunset
    )
}

@Composable
fun Buttons(
    onNavigateToHamburg: () -> Unit,
    onNavigateToBerlin: () -> Unit,
    onNavigateToFrankfurt: () -> Unit,
    onNavigateToKiel: () -> Unit,
    onNavigateToSunset: () -> Unit
) {
    Row {
        Button(onClick = onNavigateToHamburg) {
            Text("Go to Hamburg Page")
        }
    }
    Spacer(modifier = Modifier.height(32.dp))
    Row {
        Button(onClick = onNavigateToBerlin) {
            Text("Go to Berlin Page")
        }
    }
    Spacer(modifier = Modifier.height(32.dp))
    Row {
        Button(onClick = onNavigateToFrankfurt) {
            Text("Go to Frankfurt Page")
        }
    }
    Spacer(modifier = Modifier.height(32.dp))
    Row {
        Button(onClick = onNavigateToKiel) {
            Text("Go to Kiel Page")
        }
    }
    Spacer(modifier = Modifier.height(32.dp))
    Row {
        Button(onClick = onNavigateToSunset) {
            Text("Show Sunset at my Location")
        }
    }
}



