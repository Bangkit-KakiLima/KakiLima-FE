package com.dicoding.ping.banner.weather

import com.google.gson.annotations.SerializedName

data class WeatherResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Address(

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

data class Data(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("Address")
	val address: Address? = null,

	@field:SerializedName("address_id")
	val address_id: Int? = null,

	@field:SerializedName("temperature")
	val temperature: Any? = null,

	@field:SerializedName("weather_desc")
	val weather_desc: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("weather_main")
	val weather_main: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

