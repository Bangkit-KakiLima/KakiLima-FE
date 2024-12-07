package com.dicoding.ping.user.locations

import LocationModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LocationModelFactory(
    private val repository: LocationRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LocationModel::class.java)) {
            LocationModel(repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
