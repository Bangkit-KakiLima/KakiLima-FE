package com.dicoding.ping.utils

import java.math.BigDecimal
import java.math.RoundingMode

class Helper {
    fun removePath(inputUrl: String): String {
        // Find the index of the last occurrence of "http"
        val index = inputUrl.lastIndexOf("http")
        // If "http" is found, return the substring starting from that index
        return if (index != -1) inputUrl.substring(index) else inputUrl
    }
    fun roundDecimal(value: String?, decimalPlaces: Int): Double {
        if (value.isNullOrEmpty() || value == "null") {
            return 0.0
        }
        return BigDecimal(value.toDouble()).setScale(decimalPlaces, RoundingMode.HALF_UP).toDouble()
    }


    fun roundToNearestInteger(value: String?): Int {
        if (value.isNullOrEmpty() || value == "null") {
            return 0
        }
        return BigDecimal(value.toDouble()).setScale(0, RoundingMode.HALF_UP).toInt()
    }

}