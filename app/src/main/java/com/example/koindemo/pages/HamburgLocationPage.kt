package com.example.koindemo.pages

import android.R
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.koindemo.viewmodels.MainViewModel
import com.example.koindemo.api.Constant
import com.example.koindemo.api.NetworkResponse
import com.example.koindemo.api.SunsetModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HamburgLocationPage(
    onNavigateToStart: () -> Unit
) {

    val hamburgViewModel: MainViewModel = koinViewModel()

    val sunsetResult = hamburgViewModel.sunsetResult.observeAsState()

    LaunchedEffect(Unit) {
        hamburgViewModel.getSunsetData(Constant.longitudeHamburg, Constant.latitudeHamburg)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Hamburg Location Page")
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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            HeadlineAndTextHamburg()
            when (val result = sunsetResult.value) {
                is NetworkResponse.Error -> {
                    Text(text = result.message)
                }

                is NetworkResponse.Loading -> {
                    CircularProgressIndicator()
                }

                is NetworkResponse.Success -> {
                    HamburgDetails(data = result.data)
                }

                null -> {
                }
            }
        }
    }
}


@Composable
fun HeadlineAndTextHamburg() {
    Row {
        Text(
            text = "Hamburg Sunset Times",
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
fun HamburgDetails(data: SunsetModel) {
    Spacer(modifier = Modifier.height(32.dp))
    Row {
        Text(text = "Sunrise in Hamburg is at:")
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = data.results.sunrise)
    }
    Row {
        Text(text = "Sunset in Hamburg is at:")
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = data.results.sunset)
    }
}




