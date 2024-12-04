package com.dicoding.ping.locations

data class Merchant(
    val id: Int,
    val user_id: Int,
    val business_name: String,
    val average_rating: Double,
    val status: String
)

data class LocationResponse(
    val id: Int,
    val merchant_id: Int,
    val latitude: String,
    val longitude: String,
    val merchant: Merchant
)
