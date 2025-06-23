package com.example.koindemo.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.koindemo.MainViewModel
import com.example.koindemo.api.Constant
import com.example.koindemo.api.NetworkResponse
import com.example.koindemo.api.SunsetModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun BerlinLocationPage() {

    val viewModel: MainViewModel = koinViewModel()
    val sunsetResult = viewModel.sunsetResult.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.getSunsetData(Constant.longitudeBerlin, Constant.latitudeBerlin)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        HeadlineAndTextBerlin()
        when (val result = sunsetResult.value) {
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }

            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }

            is NetworkResponse.Success -> {
                BerlinDetails(data = result.data)
            }

            null -> {
            }
        }
    }
}


@Composable
fun HeadlineAndTextBerlin() {
    Row {
        Text(
            text = "Berlin Sunset Times",
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
fun BerlinDetails(data: SunsetModel) {
    Spacer(modifier = Modifier.height(32.dp))
    Row {
        Text(text = "Sunrise in Berlin is at:")
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = data.results.sunrise)
    }
    Row {
        Text(text = "Sunset in Berlin is at:")
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = data.results.sunset)
    }
}




