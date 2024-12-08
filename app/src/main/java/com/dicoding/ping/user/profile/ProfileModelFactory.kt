package com.dicoding.ping.user.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProfileModelFactory(private val repository: ProfileRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}