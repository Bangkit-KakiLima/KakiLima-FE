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
import com.dicoding.ping.databinding.ActivityOtpForgotPasswordBinding
import com.dicoding.ping.utils.SessionManager


class OtpForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpForgotPasswordBinding
    private lateinit var repository: AuthRepository
    private lateinit var sessionManager: SessionManager

    private val otpModel: OtpModel by viewModels {
        OtpModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = AuthRepository.getInstance(RetrofitClient.apiService)
        sessionManager = SessionManager(this)
        otpModel.setSessionManager(sessionManager)
        setupAction()
    }

    private fun setupAction() {
        binding.btnVerify.setOnClickListener {
            val intentNewPasswordActivity = Intent(this, NewPasswordActivity::class.java)
            val otp_code = binding.etOtp.text.toString()

            if (binding.etOtp.error == null) {
                if(otp_code == sessionManager.getOtpForgotPassword()) {
                    AlertDialog.Builder(this).apply {
                        setTitle("Yeah!")
                        setMessage("Otp benar")
                        setPositiveButton("Lanjut") { _, _ ->
                            startActivity(intentNewPasswordActivity)
                            finish()
                        }
                        create()
                        show()
                    }
                } else {
                    AlertDialog.Builder(this).apply {
                        setTitle("Oops!")
                        setMessage("Otp salah")
                        setPositiveButton("Ulangi", null)
                        create()
                        show()
                    }
                }
            } else {
                AlertDialog.Builder(this).apply {
                    setTitle("Oops!")
                    setMessage("Otp salah")
                    setPositiveButton("Ulangi", null)
                    create()
                    show()
                }
            }
        }

        binding.txtResendOtp.setOnClickListener {
            val email = sessionManager.getEmailForgotPassword()
            if (email != null) {
                otpModel.resendOtpForgotPassword(email) { success ->
                    if (success ) {
                        AlertDialog.Builder(this).apply {
                            setTitle("OTP Sent")
                            setMessage("OTP has been resent to $email.")
                            setPositiveButton("Lanjut", null)
                            create()
                            show()
                        }
                    } else {
                        AlertDialog.Builder(this).apply {
                            setTitle("Error")
                            setMessage("Failed to resend OTP. Please try again.")
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