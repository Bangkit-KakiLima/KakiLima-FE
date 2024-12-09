package com.dicoding.ping.user.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.ping.user.profile.address.NewAddressData
import com.dicoding.ping.utils.SessionManager

class ProfileModel(private val repository: ProfileRepository) : ViewModel() {

    val addAddressResponse: LiveData<List<NewAddressData>> = repository.addAddressResponse

    private lateinit var sessionManager: SessionManager

    fun setSessionManager(sessionManager: SessionManager) {
        this.sessionManager = sessionManager
    }
}