package com.example.koindemo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koindemo.MainRepositoryInterface
import com.example.koindemo.api.NetworkResponse
import com.example.koindemo.api.SunsetModel
import kotlinx.coroutines.launch

class HamburgViewModel(
    private val repository: MainRepositoryInterface
) : ViewModel() {

    private val _sunsetResult = MutableLiveData<NetworkResponse<SunsetModel>>()
    val sunsetResult: LiveData<NetworkResponse<SunsetModel>> = _sunsetResult

    fun getSunsetData(longitude: String, latitude: String) {
        _sunsetResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = repository.getSunsetData(longitude = longitude, latitude = latitude)
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
}