package com.example.koindemo

package com.example.koindemo

package com.example.koindemo

import androidx.compose.runtime.LaunchedEffect
import android.R.attr.data
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.lang.invoke.MethodHandles.constant

@Composable
fun FrankfurtLocationPage(viewModel: MainViewModel, navController: NavController) {

    val sunsetResult = viewModel.sunsetResult.observeAsState()

    LaunchedEffect(Unit) {
        {
            viewModel.getSunsetData(
                longitude = Constant.longitudeFrankfurt,
                latitude = Constant.latitudeFrankfurt
            )
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        HeadlineAndTextFrankfurt()
        when (val result = sunsetResult.value) {
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }

            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }

            is NetworkResponse.Success -> {
                FrankfurtDetails(data = result.data)
            }

            null -> {
            }
        }
    }
}


@Composable
fun HeadlineAndTextFrankfurt() {
    Row {
        Text(
            text = "Fraknfurt Sunset Times",
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
fun FrankfurtDetails(data: SunsetModel) {
    Spacer(modifier = Modifier.height(32.dp))
    Row {
        Text(text = "Sunrise in Frankfurt is at:")
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = data.results.sunrise)
    }
    Row {
        Text(text = "Sunset in Frankfurt is at:")
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = data.results.sunset)
    }
}




