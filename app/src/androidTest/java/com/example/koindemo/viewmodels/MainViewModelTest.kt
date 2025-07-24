package com.example.koindemo.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.koindemo.MainRepository
import com.example.koindemo.api.Api
import com.example.koindemo.api.Constant.latitude
import com.example.koindemo.api.Constant.longitude
import com.example.koindemo.api.Constant.testErrorLatitude
import com.example.koindemo.api.Constant.testErrorLongitude
import com.example.koindemo.api.NetworkResponse
import com.example.koindemo.api.Results
import com.example.koindemo.api.SunsetModel
import com.example.koindemo.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val results = Results(
        astronomical_twilight_begin = "00:00",
        astronomical_twilight_end = "00:00",
        civil_twilight_begin = "00:00",
        civil_twilight_end = "00:00",
        day_length = "00:00",
        nautical_twilight_begin = "00:00",
        nautical_twilight_end = "00:00",
        solar_noon = "00:00",
        sunrise = "00:00",
        sunset = "00:00"
    )
    val sunsetModel = SunsetModel(
        results = results,
        status = "OK",
        tzid = "Europe/Berlin"
    )

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        viewModel = MainViewModel(MainRepository(TestAPI(), context = appContext))
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testStatus_Loading() = runTest {
        val dispatcher = StandardTestDispatcher()
        Dispatchers.setMain(dispatcher)
        val value = viewModel.sunsetResult.value
        assertNull(value)
        viewModel.getSunsetData(longitude, latitude)
        dispatcher.scheduler.advanceUntilIdle() // Waits for coroutine completion
        val newValue = viewModel.sunsetResult.getOrAwaitValue()
        assertEquals(NetworkResponse.Success(sunsetModel), newValue)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testStatus_Error() = runTest {
        val dispatcher = StandardTestDispatcher()
        Dispatchers.setMain(dispatcher)
        val value = viewModel.sunsetResult.value
        assertNull(value)
        viewModel.getSunsetData(testErrorLongitude, testErrorLatitude)
        dispatcher.scheduler.advanceUntilIdle() // Waits for coroutine completion
        val newValue = viewModel.sunsetResult.getOrAwaitValue()
        assertTrue(newValue is NetworkResponse.Error)
        assertEquals("Failed to load data.", (newValue as NetworkResponse.Error).message)
    }

}

class TestAPI : Api {
    override suspend fun callSunsetApi(
        longitude: String,
        latitude: String,
        formatted: Int,
        timezone: String
    ): Response<SunsetModel> {
        val results = Results(
            astronomical_twilight_begin = "00:00",
            astronomical_twilight_end = "00:00",
            civil_twilight_begin = "00:00",
            civil_twilight_end = "00:00",
            day_length = "00:00",
            nautical_twilight_begin = "00:00",
            nautical_twilight_end = "00:00",
            solar_noon = "00:00",
            sunrise = "00:00",
            sunset = "00:00"
        )
        val sunsetModel = SunsetModel(
           results = results,
           status = "OK",
           tzid = "Europe/Berlin"
       )
        return Response.success(sunsetModel)
    }



}