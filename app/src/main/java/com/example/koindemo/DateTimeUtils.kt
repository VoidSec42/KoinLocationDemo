package com.example.koindemo.utils

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

object DateTimeUtils {

    // Input formatter for time strings like "1:56:20 AM" or "10:30:00 PM" from API
    private val API_UTC_TIME_INPUT_FORMATTER: DateTimeFormatter =
        DateTimeFormatter.ofPattern("h:mm:ss a", Locale.ENGLISH)

    // --- Desired OUTPUT Formats (24-hour) ---
    // For displaying the time in "Europe/Berlin" (or any target zone) in 24-hour format
    val DISPLAY_TARGET_ZONE_24H_FORMATTER: DateTimeFormatter =
        DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ENGLISH) // e.g., "04:15:42" or "18:28:25"

    // For displaying the time in UTC in 24-hour format
    val DISPLAY_UTC_24H_FORMATTER: DateTimeFormatter =
        DateTimeFormatter.ofPattern("HH:mm:ss 'UTC'", Locale.ENGLISH) // e.g., "04:15:42 UTC"

    // For displaying the time in the device's current local timezone in 24-hour format
    val DISPLAY_DEVICE_LOCAL_24H_FORMATTER: DateTimeFormatter =
        DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ENGLISH)


    /**
     * Holds the formatted time strings for an event.
     * All output times are now in 24-hour format.
     */
    data class TimeZoneFormattedTimes24H(
        val targetZoneFormatted: String?, // Time local to the specified targetZoneId (e.g., Europe/Berlin)
        val utcFormatted: String?,         // Time in UTC
        val deviceLocalFormatted: String? // Time local to the device running the app
    )

    /**
     * Processes an API time string (assumed to be UTC but formatted with AM/PM).
     * Converts this UTC time to a target timezone (e.g., "Europe/Berlin") and
     * to the device's local timezone, formatting all in 24-hour format.
     *
     * @param apiUtcTimeString The time string from the API (e.g., "4:15:42 AM"), which is stated by API to be UTC.
     * @param targetZoneId The IANA Timezone ID to which you want to convert the UTC time
     *                     (e.g., ZoneId.of("Europe/Berlin")).
     * @return TimeZoneFormattedTimes24H containing 24-hour formatted strings, or null on failure.
     */
    fun processApiUtcTimeToAllZones24H(
        apiUtcTimeString: String?,
        targetZoneId: ZoneId // e.g., ZoneId.of("Europe/Berlin")
    ): TimeZoneFormattedTimes24H? {
        if (apiUtcTimeString.isNullOrBlank()) {
            println("DateTimeUtils: API UTC time string is null or blank.")
            return null
        }

        try {
            val utcLocalTime = LocalTime.parse(apiUtcTimeString, API_UTC_TIME_INPUT_FORMATTER)
            // The date is effectively "today UTC" because the time is already UTC.
            val utcDate = LocalDate.now(ZoneId.of("UTC"))

            // 1. Create the ZonedDateTime for the event in UTC
            val eventZonedDateTimeUtc = ZonedDateTime.of(utcDate, utcLocalTime, ZoneId.of("UTC"))

            // 2. Convert to the target timezone (e.g., "Europe/Berlin")
            val targetZonedDateTime = eventZonedDateTimeUtc.withZoneSameInstant(targetZoneId)

            // 3. Convert to the device's current system timezone
            val deviceSystemZoneId = ZoneId.systemDefault()
            val deviceLocalZonedDateTime = eventZonedDateTimeUtc.withZoneSameInstant(deviceSystemZoneId)

            return TimeZoneFormattedTimes24H(
                targetZoneFormatted = targetZonedDateTime.format(DISPLAY_TARGET_ZONE_24H_FORMATTER),
                utcFormatted = eventZonedDateTimeUtc.format(DISPLAY_UTC_24H_FORMATTER), // Display original UTC
                deviceLocalFormatted = deviceLocalZonedDateTime.format(DISPLAY_DEVICE_LOCAL_24H_FORMATTER)
            )

        } catch (e: DateTimeParseException) {
            println("DateTimeUtils: Failed to parse API UTC time string '$apiUtcTimeString'. Error: ${e.message}")
            return null
        } catch (e: java.time.DateTimeException) {
            println("DateTimeUtils: DateTimeException for API UTC time '$apiUtcTimeString' with target zone '$targetZoneId'. Error: ${e.message}")
            return null
        } catch (e: Exception) {
            println("DateTimeUtils: Generic error processing API UTC time '$apiUtcTimeString' for target zone '$targetZoneId'. Error: ${e.message}")
            return null
        }
    }
}
