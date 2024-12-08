package com.dicoding.ping.user.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.ping.api.ApiService
import com.dicoding.ping.user.profile.address.AddAdressRequest
import com.dicoding.ping.user.profile.address.GetAddressResponse
import com.dicoding.ping.user.profile.address.NewAddressData
import retrofit2.HttpException

class ProfileRepository(private val apiService: ApiService) {

    private val _addAddressResponse = MutableLiveData<List<NewAddressData>>()
    val addAddressResponse: LiveData<List<NewAddressData>> get() = _addAddressResponse

    private val _getAddressResponse = MutableLiveData<List<GetAddressResponse>>()
    val getAddressResponse: LiveData<List<GetAddressResponse>> get() = _getAddressResponse

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    suspend fun addAddress(addressName: String) {
        try {
            Log.d("ProfileRepository", "Requesting to add address: $addressName")
            val request = AddAdressRequest(addressName)
            val response = apiService.addAddress(request)
            val dataList = listOf(response.data)
            _addAddressResponse.postValue(dataList as List<NewAddressData>?)
        } catch (e: HttpException) {
            Log.e("ProfileRepository", "HTTP error: ${e.code()} - ${e.message()}")
            _errorMessage.postValue("Failed to add address: HTTP ${e.code()} - ${e.message()}")
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Unexpected error: ${e.message}")
            _errorMessage.postValue("Unexpected error1: ${e.message}")
        }
    }

    suspend fun fetchAddress() {
         try {
            val response = apiService.getAddress()
             Log.d("ProfileRepository", "fetchAddress: $response")
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Error fetching address: ${e.message}")
        }
    }


}
