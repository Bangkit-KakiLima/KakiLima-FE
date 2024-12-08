package com.dicoding.ping.user


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(UserModel::class.java)) {
            UserModel() as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}