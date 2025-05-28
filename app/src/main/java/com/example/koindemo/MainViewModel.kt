package com.example.koindemo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koindemo.api.Constant
import com.example.koindemo.api.NetworkResponse
import com.example.koindemo.api.SunsetModel
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MainRepositoryInterface, private val context: Context
) : ViewModel() {

    private val _sunsetResult = MutableLiveData<NetworkResponse<SunsetModel>>()
    val sunsetResult: LiveData<NetworkResponse<SunsetModel>> = _sunsetResult
    val requestPermissionCode = 10001

    fun getSunsetData(longitude: String, latitude: String) {
        _sunsetResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = repository.getSunsetData(Constant.longitude, Constant.latitude)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _sunsetResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _sunsetResult.value = NetworkResponse.Error("Failed to load data.")
                }
            } catch (e: Exception) {
                _sunsetResult.value = NetworkResponse.Error("Failed to load data.")
            }
        }
    }

    fun getLocation() {
        viewModelScope.launch {
            repository.getLastLocation()
        }
    }

}