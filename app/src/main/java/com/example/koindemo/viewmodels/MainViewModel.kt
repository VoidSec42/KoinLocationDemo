package com.example.koindemo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koindemo.MainRepositoryInterface
import com.example.koindemo.api.LocationModel
import com.example.koindemo.api.NetworkResponse
import com.example.koindemo.api.SunsetModel
import com.example.koindemo.utils.DateTimeUtils // Ensure this is imported
import kotlinx.coroutines.launch
import java.time.ZoneId

// DisplayableSunTimes now uses TimeZoneFormattedTimes24H
data class DisplayableSunTimes(
    val astronomicalTwilightBegin: DateTimeUtils.TimeZoneFormattedTimes24H?,
    val astronomicalTwilightEnd: DateTimeUtils.TimeZoneFormattedTimes24H?,
    val civilTwilightBegin: DateTimeUtils.TimeZoneFormattedTimes24H?,
    val civilTwilightEnd: DateTimeUtils.TimeZoneFormattedTimes24H?,
    val dayLength: String,
    val nauticalTwilightBegin: DateTimeUtils.TimeZoneFormattedTimes24H?,
    val nauticalTwilightEnd: DateTimeUtils.TimeZoneFormattedTimes24H?,
    val solarNoon: DateTimeUtils.TimeZoneFormattedTimes24H?,
    val sunrise: DateTimeUtils.TimeZoneFormattedTimes24H?,
    val sunset: DateTimeUtils.TimeZoneFormattedTimes24H?
)

class MainViewModel(
    private val repository: MainRepositoryInterface
) : ViewModel() {

    private val _sunsetResult = MutableLiveData<NetworkResponse<DisplayableSunTimes>>()
    val sunsetResult: LiveData<NetworkResponse<DisplayableSunTimes>> = _sunsetResult

    private val _locationResult = MutableLiveData<LocationModel?>()
    val locationResult: LiveData<LocationModel?> = _locationResult

    // This is the ZoneId you want to display as one of the "local" options,
    // despite the API returning times in UTC.
    private val displayTargetZoneId: ZoneId = try {
        ZoneId.of("Europe/Berlin")
    } catch (e: Exception) {
        println("ViewModel: Invalid displayTargetZoneId. Falling back to system default.")
        ZoneId.systemDefault()
    }

    fun getSunsetData(longitude: String, latitude: String) {
        _sunsetResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                // The tzid parameter in repository.getSunsetData might be used by the API
                // to determine the *date* for the sunrise/sunset, but the API response
                // times themselves are in UTC with AM/PM format.
                val response = repository.getSunsetData(
                    longitude = longitude,
                    latitude = latitude
                )

                if (response.isSuccessful) {
                    response.body()?.let { originalSunsetModel ->
                        val originalResults = originalSunsetModel.results

                        // The API itself says its times are UTC, so we use displayTargetZoneId
                        // for the "Europe/Berlin" conversion.
                        val displayableTimes = DisplayableSunTimes(
                            astronomicalTwilightBegin = DateTimeUtils.processApiUtcTimeToAllZones24H(originalResults.astronomical_twilight_begin, displayTargetZoneId),
                            astronomicalTwilightEnd = DateTimeUtils.processApiUtcTimeToAllZones24H(originalResults.astronomical_twilight_end, displayTargetZoneId),
                            civilTwilightBegin = DateTimeUtils.processApiUtcTimeToAllZones24H(originalResults.civil_twilight_begin, displayTargetZoneId),
                            civilTwilightEnd = DateTimeUtils.processApiUtcTimeToAllZones24H(originalResults.civil_twilight_end, displayTargetZoneId),
                            dayLength = originalResults.day_length,
                            nauticalTwilightBegin = DateTimeUtils.processApiUtcTimeToAllZones24H(originalResults.nautical_twilight_begin, displayTargetZoneId),
                            nauticalTwilightEnd = DateTimeUtils.processApiUtcTimeToAllZones24H(originalResults.nautical_twilight_end, displayTargetZoneId),
                            solarNoon = DateTimeUtils.processApiUtcTimeToAllZones24H(originalResults.solar_noon, displayTargetZoneId),
                            sunrise = DateTimeUtils.processApiUtcTimeToAllZones24H(originalResults.sunrise, displayTargetZoneId),
                            sunset = DateTimeUtils.processApiUtcTimeToAllZones24H(originalResults.sunset, displayTargetZoneId)
                        )
                        _sunsetResult.value = NetworkResponse.Success(displayableTimes)

                    } ?: run {
                        _sunsetResult.value = NetworkResponse.Error("Response body is null.")
                    }
                } else {
                    _sunsetResult.value = NetworkResponse.Error("Failed to load data. Code: ${response.code()}")
                }
            } catch (e: Exception) {
                _sunsetResult.value = NetworkResponse.Error("Failed to load data: ${e.message}")
                e.printStackTrace() // Good for debugging
            }
        }
    }

    fun getLocation() {
        _locationResult.value = null
        viewModelScope.launch {
            _locationResult.value = repository.getLastLocation()
        }
    }
}
