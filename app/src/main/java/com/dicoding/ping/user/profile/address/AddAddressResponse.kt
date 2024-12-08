package com.dicoding.ping.user.profile.address

import com.google.gson.annotations.SerializedName

data class AddAddressResponse(

    @field:SerializedName("data")
    val data: NewAddressData? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class NewAddressData(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("user_id")
    val user_id: Int? = null,

    @field:SerializedName("latitude")
    val latitude: Any? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("address_name")
    val address_name: String? = null,

    @field:SerializedName("longitude")
    val longitude: Any? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)