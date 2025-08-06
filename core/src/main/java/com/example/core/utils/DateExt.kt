package com.example.core.utils

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

fun Long.toReadableDate(): String {
    val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return formatter.format(Date(this))
}

fun Timestamp.formatFirebaseTimestamp(): String {
    val date = this.toDate()
    val formatter = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
    return formatter.format(date)
}

fun Long.formatDay(): String {
    val sdf = SimpleDateFormat("EEE", Locale.getDefault())
    return sdf.format(Date(this))
}

fun Long.formatTimestamp(): String {
    val now = System.currentTimeMillis()
    val diffMillis = now - this
    val diffDays = TimeUnit.MILLISECONDS.toDays(diffMillis).toInt()

    return when (diffDays) {
        0 -> {
            val diffSeconds = TimeUnit.MILLISECONDS.toSeconds(diffMillis)
            val diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis)
            val diffHours = TimeUnit.MILLISECONDS.toHours(diffMillis)

            when {
                diffSeconds < 60 -> "$diffSeconds seconds ago"
                diffMinutes < 60 -> "$diffMinutes minutes ago"
                else -> "$diffHours hours ago"
            }
        }
        1 -> {
            val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
            val timeString = timeFormat.format(Date(this))
            "Yesterday @ $timeString"
        }
        in 2..6 -> "$diffDays days ago"
        else -> {
            val dateFormat = SimpleDateFormat("MMM dd", Locale.getDefault())
            dateFormat.format(Date(this))
        }
    }
}

fun Long.toLocalDate(): LocalDate {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}