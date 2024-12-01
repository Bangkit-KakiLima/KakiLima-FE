package com.dicoding.ping.auth.password

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ping.API.RetrofitClient
import com.dicoding.ping.auth.AuthRepository
import com.dicoding.ping.auth.otp.OtpModel
import com.dicoding.ping.auth.otp.OtpModelFactory
import com.dicoding.ping.databinding.ActivityForgotPasswordBinding
import com.dicoding.ping.utils.SessionManager


class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var repository: AuthRepository
    private lateinit var sessionManager: SessionManager
    private lateinit var binding: ActivityForgotPasswordBinding

    private val otpModel: OtpModel by viewModels {
        OtpModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = AuthRepository.getInstance(RetrofitClient.apiService)
        sessionManager = SessionManager(this)
        otpModel.setSessionManager(sessionManager)
        binding.btnSubmitFgPassword.setOnClickListener {
            val email = binding.etForgotPassword.text.toString()
            if (email != null) {
                val intent = Intent(this, OtpForgotPasswordActivity::class.java)
                otpModel.resendOtpForgotPassword(email) { success ->
                    if (success) {
                        sessionManager.saveEmailForgotPassword(email)
                        AlertDialog.Builder(this).apply {
                            setTitle("OTP Sent")
                            setMessage("An OTP has been sent to your email $email.")
                            setPositiveButton("Continue") { _, _ ->
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    } else {
                        AlertDialog.Builder(this).apply {
                            setTitle("Failed to Send OTP")
                            setMessage("Failed to send OTP. Please try again.")
                            setPositiveButton("Retry", null)
                            create()
                            show()
                        }
                    }

                }
            }
        }
    }

}