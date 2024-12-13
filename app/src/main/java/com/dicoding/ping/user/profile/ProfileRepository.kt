package com.dicoding.ping.user.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.ping.api.ApiService
import com.dicoding.ping.user.profile.address.AddAdressRequest
import com.dicoding.ping.user.profile.address.GetAddressResponse
import com.dicoding.ping.user.profile.address.NewAddressData
import retrofit2.HttpException

class ProfileRepository(private val apiService: ApiService, requireContext: Context) {

    private val _addAddressResponse = MutableLiveData<List<NewAddressData>>()
    val addAddressResponse: LiveData<List<NewAddressData>> get() = _addAddressResponse

    private val _getAddressResponse = MutableLiveData<List<GetAddressResponse>>()
    val getAddressResponse: LiveData<List<GetAddressResponse>> get() = _getAddressResponse

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    suspend fun addAddress(lat: String, lon: String) {
        try {
            Log.d("ProfileRepository", "Requesting to add address: lat=$lat, lon=$lon")
            val request = AddAdressRequest(lat, lon)
            val response = apiService.addAddress(request)
            val dataList = listOf(response.data)
            _addAddressResponse.postValue(dataList as List<NewAddressData>?)
        } catch (e: HttpException) {
            Log.e("ProfileRepository", "HTTP error: ${e.code()} - ${e.response()?.errorBody()?.string()}")
            _errorMessage.postValue("Failed to add address: HTTP ${e.code()} - ${e.message()}")
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Unexpected error: ${e.message}")
            _errorMessage.postValue("Unexpected error: ${e.message}")
        }
    }

    suspend fun checkAndSaveAddress(lat: String, lon: String) {
        try {
            val addRequest = AddAdressRequest(lat, lon)
            val response = apiService.addAddress(addRequest)
            Log.d("ProfileRepository", "Data added successfully: ${response.data}")
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Error in checkAndSaveAddress: ${e.message}")
        }
    }

}
