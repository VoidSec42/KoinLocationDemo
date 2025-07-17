package com.example.koindemo.api


import com.google.common.truth.Truth.assertThat
import org.junit.Test

class LocationModelTest {

    @Test
    fun emptyLatitude_returnsFalse() {
        val result = validateLocationModel(
            latitude = "",
            longitude = "100.121232"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun emptyLongitude_returnsFalse() {
        val result = validateLocationModel(
            latitude = "9.9999",
            longitude = ""
        )
        assertThat(result).isFalse()
    }

}