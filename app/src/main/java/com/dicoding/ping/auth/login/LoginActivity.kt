package com.dicoding.ping.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ping.API.RetrofitClient
import com.dicoding.ping.databinding.ActivityLoginBinding
import com.dicoding.ping.auth.AuthRepository
import com.dicoding.ping.auth.password.ForgotPasswordActivity
import com.dicoding.ping.auth.register.RegisterActivity
import com.dicoding.ping.user.home.MainActivity
import com.dicoding.ping.utils.SessionManager

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var repository: AuthRepository
    private lateinit var sessionManager: SessionManager

    private val loginModel: LoginModel by viewModels {
        LoginModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = AuthRepository.getInstance(RetrofitClient.apiService)
        sessionManager = SessionManager(this)
        loginModel.setSessionManager(sessionManager)
        setupAction()
    }

    private fun setupAction() {
        binding.txtSignUp.setOnClickListener {
            val intentRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegister)
        }

        binding.txtForgotPassword.setOnClickListener {
            val intentForgotPasword = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intentForgotPasword)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            Log.d("setupAction Login 2", "setupAction: $email, $password")
            if (email.isEmpty() || password.isEmpty()) {
                AlertDialog.Builder(this).apply {
                    setTitle("Opps!")
                    setMessage("Incorrect email and password")
                    setPositiveButton("Repeat", null)
                    create()
                    show()
                }
            } else {
                Log.d("setupAction Login 1", "setupAction: $email, $password")
                loginModel.login(email, password) { success ->
                    if (success) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        AlertDialog.Builder(this).apply {
                            setTitle("Opps!")
                            setMessage("Incorrect email and password")
                            setPositiveButton("Repeat", null)
                            create()
                            show()
                        }
                    }
                }
            }
        }
    }
}