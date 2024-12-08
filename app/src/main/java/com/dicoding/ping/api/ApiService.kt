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
import com.dicoding.ping.user.locations.LocationResponse
import com.dicoding.ping.user.UserDataResponse
import com.dicoding.ping.user.home.product.GetAllProductResponse
import com.dicoding.ping.user.home.product.ProductDetail
import com.dicoding.ping.user.profile.address.AddAddressResponse
import com.dicoding.ping.user.profile.address.AddAdressRequest
import com.dicoding.ping.user.profile.address.GetAddressResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {
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
    suspend fun getProductById(@Path("id") id: Int): Response<ProductDetail>

    //    Weather - Address
    @GET("address/weather")
    suspend fun getWeather(): WeatherResponse

    @GET("address")
    suspend fun getAddress(): GetAddressResponse

    @POST("address")
    suspend fun addAddress(@Body request: AddAdressRequest): AddAddressResponse

    //    Location
    @GET("api/location")
    suspend fun getAllLocations(): List<LocationResponse>

    //     Product Category
    @GET("product/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): GetAllProductResponse

}