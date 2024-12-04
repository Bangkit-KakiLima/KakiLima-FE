package com.dicoding.ping.kategori

data class Kategori(
    val imageResource: Int,
    val name: String,
    val rating: Double,
    val distance: String,
    val price: String,
    val isOpen: Boolean
) {

    fun getOpenStatus(): String {
        return if (isOpen) "Open" else "Closed"
    }
}
