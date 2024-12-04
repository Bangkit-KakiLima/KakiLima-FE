package com.dicoding.ping.user.locations.model

class Lokasi(
    @kotlin.jvm.JvmField val name: String,
    val distance: String,
    val time: String,
    val imageResource: Int,
    val rating: Double,
    val isOpen: Boolean
)