package com.dicoding.ping.user.locations.model

class Lokasi(
    @JvmField val name: String,
    val imageUrl: String,
    val rating: Double,
    val isOpen: Boolean,
    val distance: Double
)