package com.dicoding.ping.user
import androidx.lifecycle.ViewModel
import com.dicoding.ping.utils.SessionManager


class UserModel(private val repository: UserRepository) : ViewModel() {
    private lateinit var sessionManager: SessionManager

    fun setSessionManager(sessionManager: SessionManager) {
        this.sessionManager = sessionManager
    }

    fun logout(){
        sessionManager.clearSession()
    }
}
