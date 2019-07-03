package com.raul.androidapps.softwareteststarling.persistence.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun getTodayDateForRecurrenceRule(): String{
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
        val date = Date()
        return sdf.format(date)
    }
}