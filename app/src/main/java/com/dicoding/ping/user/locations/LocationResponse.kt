package com.dicoding.ping.user.locations

data class Product(
    val id: Int,
    val merchant_id: Int,
    val name: String,
    val image: String
)

data class Merchant(
    val id: Int,
    val user_id: Int,
    val business_name: String,
    val average_rating: Double,
    val status: String,
    val products: List<Product>
)

data class LocationResponse(
    val id: Int,
    val merchant_id: Int,
    val latitude: String,
    val longitude: String,
    val merchant: Merchant
)
