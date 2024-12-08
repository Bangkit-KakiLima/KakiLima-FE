package com.dicoding.ping.user.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.ping.user.profile.address.GetAddressResponse
import com.dicoding.ping.user.profile.address.NewAddressData
import com.dicoding.ping.utils.SessionManager
import kotlinx.coroutines.launch

class ProfileModel(private val repository: ProfileRepository) : ViewModel() {

    val addAddressResponse: LiveData<List<NewAddressData>> = repository.addAddressResponse

    val getAddressResponse: LiveData<List<GetAddressResponse>> = repository.getAddressResponse

    val errorMessage: LiveData<String> = repository.errorMessage

    private lateinit var sessionManager: SessionManager

    fun setSessionManager(sessionManager: SessionManager) {
        this.sessionManager = sessionManager
    }

    fun addAddress(addressName: String) {
        viewModelScope.launch {
            repository.addAddress(addressName)
        }
    }

    fun fetchAddress() {
        viewModelScope.launch {
            repository.fetchAddress()
        }
    }
}