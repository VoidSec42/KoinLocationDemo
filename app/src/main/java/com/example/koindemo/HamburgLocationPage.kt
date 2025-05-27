package com.example.koindemo

import androidx.compose.runtime.LaunchedEffect
import android.R.attr.data
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
import java.lang.invoke.MethodHandles.constant

@Composable
fun HamburgLocationPage(viewModel: MainViewModel) {

    val sunsetResult = viewModel.sunsetResult.observeAsState()
    var inputLatitude by remember { mutableStateOf("") }
    var inputLongitude by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
                {
                    viewModel.getSunsetData(
                        longitude = Constant.longitudeHamburg,
                        latitude = Constant.latitudeHamburg
                    )
                }
            }

            when (val result = sunsetResult.value) {
                is NetworkResponse.Error -> {
                    Text(text = result.message)
                }

                NetworkResponse.Loading -> {
                    CircularProgressIndicator()
                }

                is NetworkResponse.Success -> {
                    HamburgDetails(data = result.data)
                }

                null -> {
                }
            }
        }

@Composable
fun HamburgDetails(data: SunsetModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
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
}




