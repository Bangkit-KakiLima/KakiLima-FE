package com.dicoding.ping.locations

import LocationsModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LocationsModelFactory(
    private val repository: LocationsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LocationsModel::class.java)) {
            LocationsModel(repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
