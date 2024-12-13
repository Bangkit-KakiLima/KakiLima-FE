package com.dicoding.ping.api

import com.dicoding.ping.auth.login.LoginRequest
import com.dicoding.ping.auth.login.LoginResponse
import com.dicoding.ping.auth.otp.OtpRequest
import com.dicoding.ping.auth.otp.OtpResponse
import com.dicoding.ping.auth.otp.ResendOtpRequest
import com.dicoding.ping.auth.otp.ResendOtpResponse
import com.dicoding.ping.auth.password.ForgotPasswordRequest
import com.dicoding.ping.auth.password.ForgotPasswordResponse
import com.dicoding.ping.auth.register.RegisterRequest
import com.dicoding.ping.auth.register.RegisterResponse
import com.dicoding.ping.banner.weather.WeatherResponse
import com.dicoding.ping.user.UserDataResponse
import com.dicoding.ping.user.home.product.GetAllProductResponse
import com.dicoding.ping.user.home.product.ProdRecFromMlResponse
import com.dicoding.ping.user.home.product.ProductDetailResponse
import com.dicoding.ping.user.locations.LocationResponse
import com.dicoding.ping.user.profile.address.AddAddressResponse
import com.dicoding.ping.user.profile.address.AddAdressRequest
import com.dicoding.ping.user.profile.address.GetAddressResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    //    Auth
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @POST("auth/verify")
    suspend fun verify(@Body request: OtpRequest): OtpResponse

    @POST("auth/resend_otp")
    suspend fun resendOtp(@Body request: ResendOtpRequest): ResendOtpResponse

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/ResetPassword")
    suspend fun resetPassword(@Body request: ForgotPasswordRequest): ForgotPasswordResponse

    @GET("auth/userData")
    suspend fun getUserData(): UserDataResponse

    //    Product
    @GET("product")
    suspend fun getAllProducts(): GetAllProductResponse

    @GET("product/recommendations")
    suspend fun getAllRecommendationProducts(): GetAllProductResponse

    @GET("product/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDetailResponse

    @GET("product/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): GetAllProductResponse

    @POST("product/ml/recommendations")
    suspend fun getProductsRecomendationByMl(): ProdRecFromMlResponse

    //    Location
    @GET("locations")
    suspend fun getAllLocations(): List<LocationResponse>

    @GET("address/weather")
    suspend fun getWeather(): WeatherResponse

    @GET("address")
    suspend fun getAddress(): GetAddressResponse

    @POST("address/location")
    suspend fun addAddress(@Body request: AddAdressRequest): AddAddressResponse

    @PUT("address/location")
    suspend fun updateAddress(@Body request: AddAdressRequest): AddAddressResponse

}