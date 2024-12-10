package com.dicoding.ping.user.home.product

import com.google.gson.annotations.SerializedName

data class ProductDetailResponse(

	@field:SerializedName("data")
	val data: DataItems? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItems(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("merchant")
	val merchant: Sellers? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("categories")
	val categories: List<CategoriesItems?>? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class ProductCategoryMappings(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("category_id")
	val category_id: Int? = null,

	@field:SerializedName("product_id")
	val product_id: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class Sellers(

	@field:SerializedName("business_name")
	val business_name: String? = null,

	@field:SerializedName("user_id")
	val user_id: Int? = null,

	@field:SerializedName("average_rating")
	val average_rating: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class CategoriesItems(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("Product_Category_Mapping")
	val product_category_mapping: ProductCategoryMappings? = null
)
