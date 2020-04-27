package com.olmi.android.memes.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun convertLongToTime(time: Long, locale: Locale): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd.MM.yyyy", locale)
        return format.format(date)
    }

    fun currentTimeToLong(): Long {
        return System.currentTimeMillis()
    }

    fun convertDateToLong(date: String, locale: Locale): Long {
        val df = SimpleDateFormat("dd.MM.yyyy", locale)
        return df.parse(date).time
    }
}
