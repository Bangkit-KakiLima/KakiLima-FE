package com.dicoding.ping.auth.password

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ping.API.RetrofitClient
import com.dicoding.ping.auth.AuthRepository
import com.dicoding.ping.auth.otp.OtpModel
import com.dicoding.ping.auth.login.LoginActivity
import com.dicoding.ping.auth.otp.OtpModelFactory
import com.dicoding.ping.databinding.ActivityNewPasswordBinding
import com.dicoding.ping.utils.SessionManager

class NewPasswordActivity : AppCompatActivity() {
    lateinit var repository: AuthRepository
    private lateinit var sessionManager: SessionManager
    private lateinit var binding: ActivityNewPasswordBinding

    private val otpModel: OtpModel by viewModels {
        OtpModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNewPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repository = AuthRepository.getInstance(RetrofitClient.apiService)
        sessionManager = SessionManager(this)
        otpModel.setSessionManager(sessionManager)
        setupAction()
    }

    private fun setupAction() {
        binding.btnSubmitNewPass.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            val newPassword = binding.etNewPassword.text.toString()
            val email = sessionManager.getEmailForgotPassword()
            val otp_code = sessionManager.getOtpForgotPassword()
            Log.d("setupAction New Password", "setupAction: $email, $otp_code, $newPassword")
            if (newPassword != null && email != null && otp_code != null) {
                otpModel.resetPassowrd(otp_code, email, newPassword) { success ->
                    if (success == true) {
                        AlertDialog.Builder(this).apply {
                            setTitle("Berhasil")
                            setMessage("Password berhasil dirubah")
                            setPositiveButton("Login") { _, _ ->
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    } else {
                        AlertDialog.Builder(this).apply {
                            setTitle("Galgal")
                            setMessage("Gagal merubah password. Silahkan coba lagi")
                            setPositiveButton("Coba lagi", null)
                            create()
                            show()
                        }
                    }
                }
            }
        }
    }
}