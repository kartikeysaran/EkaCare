package com.yekitrak.ekacare.data.helper

import java.text.SimpleDateFormat
import java.util.Date

class Helper {
    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return formatter.format(Date(millis))
    }
}