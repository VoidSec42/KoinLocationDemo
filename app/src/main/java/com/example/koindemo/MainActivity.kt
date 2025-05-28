package com.example.koindemo

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.example.koindemo.pages.StartPage
import com.example.koindemo.ui.theme.KoinDemoTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import org.koin.androidx.viewmodel.ext.android.getViewModel
import kotlin.random.Random


class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    val viewModel = getViewModel<MainViewModel>()
                    // Check Permissions
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        // Then open StartPage
                        Toast.makeText(this, "Permissions are granted", Toast.LENGTH_SHORT).show()
                        StartPage(viewModel, navController)
                    } else {
                        // Request Permission if false
                        val requestCode = Random.nextInt(1000, 10000)
                        // Then set Permission
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(
                                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                android.Manifest.permission.ACCESS_FINE_LOCATION
                            ),
                            requestCode
                        )
                        // And open StartPage
                        Toast.makeText(this, "Permissions are not granted", Toast.LENGTH_SHORT)
                            .show()
                        StartPage(viewModel, navController)
                    }
                }
            }
        }
    }
}


