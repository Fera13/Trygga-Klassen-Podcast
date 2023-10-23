package com.example.tryggaklassenpod.helperFunctions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Int.toHoursMinuteSeconds(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val remainingSeconds = this % 60

    return if (hours == 0) String.format("%02d:%02d", minutes, remainingSeconds) else String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
}

fun Long.getCreatedAtAsDate(): Date {
    return Date(this)
}

fun Long.getCreatedAtFormatted(): String {
    val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy, HH:mm", Locale.getDefault())
    val date = Date(this)
    return dateFormat.format(date)
}