package com.example.koindemo.pages

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.koindemo.viewmodels.KielViewModel
import com.example.koindemo.api.Constant
import com.example.koindemo.api.NetworkResponse
import com.example.koindemo.api.SunsetModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KielLocationPage(
    onNavigateToStart: () -> Unit
) {

    val viewModel: KielViewModel = koinViewModel()
    val sunsetResult = viewModel.sunsetResult.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.getSunsetData(
            longitude = Constant.longitudeKiel,
            latitude = Constant.latitudeKiel
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Kiel Location Page")
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateToStart) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { values ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(42.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            HeadlineAndTextKiel()
            when (val result = sunsetResult.value) {
                is NetworkResponse.Error -> {
                    Text(text = result.message)
                }

                is NetworkResponse.Loading -> {
                    CircularProgressIndicator()
                }

                is NetworkResponse.Success -> {
                    KielDetails(data = result.data)
                }

                null -> {
                }
            }
        }
    }
}


@Composable
fun HeadlineAndTextKiel() {
    Row {
        Text(
            text = "Kiel Sunset Times",
            fontSize = 22.sp,
            textAlign = TextAlign.Center
        )
    }
    Spacer(modifier = Modifier.height(32.dp))
    Row {
        Text(
            text = "For now this page shows hardcoded sunset and sunrise data for this location when opened.",
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun KielDetails(data: SunsetModel) {
    Spacer(modifier = Modifier.height(32.dp))
    Row {
        Text(text = "Sunrise in Kiel is at:")
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = data.results.sunrise)
    }
    Row {
        Text(text = "Sunset in Kiel is at:")
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = data.results.sunset)
    }
}




