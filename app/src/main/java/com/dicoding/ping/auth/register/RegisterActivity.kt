package com.dicoding.ping.auth.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ping.API.RetrofitClient
import com.dicoding.ping.auth.AuthRepository
import com.dicoding.ping.auth.login.LoginActivity
import com.dicoding.ping.auth.otp.OtpRegisterActivity
import com.dicoding.ping.databinding.ActivityRegisterBinding
import com.dicoding.ping.utils.SessionManager

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var repository: AuthRepository
    private lateinit var sessionManager: SessionManager

    private val registerModel: RegisterModel by viewModels {
        RegisterModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = AuthRepository.getInstance(RetrofitClient.apiService)
        sessionManager = SessionManager(this)

        setupView()
        setupAction()
    }

    private fun setupView() {
    }

    private fun setupAction() {
        binding.txtLogin.setOnClickListener {
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        }

        binding.btnRegister.setOnClickListener {
            val intentRegister = Intent(this, RegisterActivity::class.java)
            val username = binding.etYourName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()
            val role = "buyer"
            if (password == confirmPassword) {
                if (binding.etEmail.error == null && binding.etPassword.error == null) {
                    registerModel.register(username, email, password, role) { success ->
                        if (!isFinishing && !isDestroyed) {
                            if (success) {
                                sessionManager.saveEmail(email)
                                AlertDialog.Builder(this).apply {
                                    setTitle("Yeah!")
                                    setMessage("The account with $email has been successfully created. Please log in to continue.")
                                    setPositiveButton("Continue") { _, _ ->
                                        finish()
                                        val intent = Intent(
                                            this@RegisterActivity,
                                            OtpRegisterActivity::class.java
                                        )
                                        startActivity(intent)
                                    }
                                    create()
                                    show()
                                }
                            } else {
                                AlertDialog.Builder(this).apply {
                                    setTitle("Oops!")
                                    setMessage("The account with $email failed to be created. Please try again.")
                                    setPositiveButton("Retry") { _, _ ->
                                        startActivity(intentRegister)
                                        finish()
                                    }
                                    create()
                                    show()
                                }
                            }
                        }
                    }
                }
            } else {
                binding.etConfirmPassword.error = "Password is not the same"
            }
        }
    }
}
