package com.dicoding.ping.auth.password

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ping.R
import com.dicoding.ping.api.RetrofitClient
import com.dicoding.ping.auth.AuthRepository
import com.dicoding.ping.auth.otp.OtpModel
import com.dicoding.ping.auth.login.LoginActivity
import com.dicoding.ping.auth.otp.OtpModelFactory
import com.dicoding.ping.databinding.ActivityNewPasswordBinding
import com.dicoding.ping.utils.SessionManager
import com.google.android.material.textfield.TextInputLayout

class NewPasswordActivity : AppCompatActivity() {
    lateinit var repository: AuthRepository
    private lateinit var sessionManager: SessionManager
    private lateinit var binding: ActivityNewPasswordBinding

    private val otpModel: OtpModel by viewModels {
        OtpModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = AuthRepository.getInstance(RetrofitClient.apiService)
        sessionManager = SessionManager(this)
        otpModel.setSessionManager(sessionManager)

        setupPasswordValidation()
        setupAction()
    }

    private fun setupAction() {
        binding.btnSubmitNewPass.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            val newPassword = binding.etNewPassword.text.toString()
            val email = sessionManager.getEmailForgotPassword()
            val otp_code = sessionManager.getOtpForgotPassword()

            if (email != null && otp_code != null) {
                binding.btnSubmitNewPass.showLoading(true)
                otpModel.resetPassowrd(otp_code, email, newPassword) { success ->

                    binding.btnSubmitNewPass.postDelayed({
                        binding.btnSubmitNewPass.showLoading(false)
                        if (success == true) {
                            AlertDialog.Builder(this).apply {
                                setTitle("Success")
                                setMessage("Password has been successfully changed")
                                setPositiveButton("Login") { _, _ ->
                                    startActivity(intent)
                                    finish()
                                }
                                create()
                                show()
                            }
                        } else {
                            AlertDialog.Builder(this).apply {
                                setTitle("Failed")
                                setMessage("Failed to change password. Please try again")
                                setPositiveButton("Retry", null)
                                create()
                                show()
                            }
                        }
                    }, 2000)
                }
            }
        }
    }

    private fun setupPasswordValidation() {
        val passwordInputLayout = findViewById<TextInputLayout>(R.id.passwordInputLayout)
        binding.etNewPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.length < 8) {
                    binding.etNewPassword.error = "Password cannot be less than 8 characters"
                    passwordInputLayout.endIconMode = TextInputLayout.END_ICON_NONE
                } else {
                    binding.etNewPassword.error = null
                    passwordInputLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}