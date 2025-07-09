package com.example.koindemo.pages

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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.koindemo.viewmodels.MainViewModel
import com.example.koindemo.api.NetworkResponse
import com.example.koindemo.api.SunsetModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SunsetPage(onNavigateToStart: () -> Unit) {

    val viewModel: MainViewModel = koinViewModel()
    val sunsetResult = viewModel.sunsetResult.observeAsState()
    val locationResult = viewModel.locationResult.observeAsState()
    var inputLatitude by remember { mutableStateOf("") }
    var inputLongitude by remember { mutableStateOf("") }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Sunset Location Page")
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
            Spacer(modifier = Modifier.height(42.dp))

            // Row for fetching the location
            Row {
                Button(
                    onClick = { viewModel.getLocation() }
                ) {
                    Text(
                        text = "Fetch Location"
                    )
                }
            }
            Spacer(modifier = Modifier.height(42.dp))

            // Row for showing the location
            Row {
                Text(text = "Location:")
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = locationResult.value?.latitude + " " + locationResult.value?.longitude)
            }
            Spacer(modifier = Modifier.height(42.dp))

            // Handling the network answer and data for SunsetAPI
            locationResult.value.let {
                if (it != null) {
                    // Row for showing the input text field for sunset API parameters
                    Row {
                        OutlinedTextField(
                            value = locationResult.value!!.latitude,
                            onValueChange = { inputLatitude = it })
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        OutlinedTextField(
                            value = locationResult.value!!.longitude,
                            onValueChange = { inputLongitude = it })
                    }
                } else {
                    // Row for showing the input text field for sunset API parameters
                    Row {
                        OutlinedTextField(
                            value = inputLatitude,
                            onValueChange = { inputLatitude = it })
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        OutlinedTextField(
                            value = inputLongitude,
                            onValueChange = { inputLongitude = it })
                    }
                }
            }

            // Row for fetching Sunset API with added parameters
            Spacer(modifier = Modifier.height(32.dp))
            Row {
                Button(
                    onClick = {
                        viewModel.getSunsetData(
                            longitude = inputLongitude,
                            latitude = inputLatitude
                        )
                    }
                ) {
                    Text(
                        text = "Fetch Sunrise Data"
                    )
                }
            }

            // Handling the network answer and data for SunsetAPI
            when (val result = sunsetResult.value) {
                is NetworkResponse.Error -> {
                    Text(text = result.message)
                }

                NetworkResponse.Loading -> {
                    CircularProgressIndicator()
                }

                is NetworkResponse.Success -> {
                    SunsetDetails(data = result.data)
                }

                null -> {
                }
            }
        }
    }
}

@Composable
fun SunsetDetails(data: SunsetModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Row {
            Text(text = "Sunrise is at:")
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = data.results.sunrise)
        }
        Row {
            Text(text = "Sunset is at:")
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = data.results.sunset)
        }
    }
}




