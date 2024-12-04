package com.dicoding.ping.utils

class Helper {
    fun removePath(inputUrl: String): String {
        // Find the index of the last occurrence of "http"
        val index = inputUrl.lastIndexOf("http")
        // If "http" is found, return the substring starting from that index
        return if (index != -1) inputUrl.substring(index) else inputUrl
    }
}