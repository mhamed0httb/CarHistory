package com.cheersapps.carhistory.utils

import java.util.*

object DateUtils {

    private const val TIMEZONE = "UTC+1"

    private const val TIME_FORMAT = "H:m"
    private const val DATE_FORMAT = "d MMMM yyyy"

    private val calendar = Calendar.getInstance()

    init {
        calendar.timeZone = TimeZone.getTimeZone(TIMEZONE)
    }

    fun currentFullDate(): Date = calendar.time

    fun currentTime(): String = android.text.format.DateFormat.format(TIME_FORMAT, calendar.time).toString()
    fun currentDate(): String = android.text.format.DateFormat.format(DATE_FORMAT, calendar.time).toString()

    fun currentFullDateTimestamp(): Long {
        return calendar.time.time
        //TimeUnit.MILLISECONDS.toSeconds(timeStampMilliSecondsDateUtils)
    }

    fun timestampToDateString(timestamp: Long): String = android.text.format.DateFormat.format(DATE_FORMAT, timestamp).toString()
    fun timestampToTimeString(timestamp: Long): String = android.text.format.DateFormat.format(TIME_FORMAT, timestamp).toString()
}