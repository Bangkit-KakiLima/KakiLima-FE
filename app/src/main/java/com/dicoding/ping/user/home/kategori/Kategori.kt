package com.dicoding.ping.user.home.kategori

data class Kategori(
    val imageResource: Int,
    val name: String,
    val rating: Double,
    val price: String,
    val isOpen: Boolean
) {

    fun getOpenStatus(): String {
        return if (isOpen) "Open" else "Closed"
    }
}
