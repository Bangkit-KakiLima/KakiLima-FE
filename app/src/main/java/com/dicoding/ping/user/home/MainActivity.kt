package com.dicoding.ping.user.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ping.api.RetrofitClient
import com.dicoding.ping.auth.login.LoginActivity
import com.dicoding.ping.databinding.ActivityMainBinding
import com.dicoding.ping.user.UserModel
import com.dicoding.ping.user.UserModelFactory
import com.dicoding.ping.user.UserRepository
import com.dicoding.ping.utils.SessionManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: UserRepository
    private lateinit var sessionManager: SessionManager
    private val userModel: UserModel by viewModels {
        UserModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = UserRepository.getInstance(RetrofitClient.apiService)
        sessionManager = SessionManager(this)
        userModel.setSessionManager(sessionManager)

        if (!sessionManager.getIsLogin()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            setupAction()
        }
    }

    private fun setupAction() {
        binding.txtName.text = sessionManager.getUsername()
        binding.button.setOnClickListener {
            userModel.logout()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}