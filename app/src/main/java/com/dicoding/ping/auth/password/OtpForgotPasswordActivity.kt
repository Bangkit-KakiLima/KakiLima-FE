package com.dicoding.ping.auth.password

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ping.api.RetrofitClient
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = AuthRepository.getInstance(RetrofitClient.apiService)
        sessionManager = SessionManager(this)
        otpModel.setSessionManager(sessionManager)

        val resendOtpTextView = binding.txtResendOtp

        // Membuat SpannableString untuk teks
        val spannableStringSignUp = SpannableString("Resend again")
        resendOtpTextView.text = spannableStringSignUp

        binding.txtResendOtp.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {

                    // Ubah warna teks menjadi warna custom (#4DA0C1) dan tambahkan underline
                    val spannableHover = SpannableString("Resend again")
                    spannableHover.setSpan(
                        ForegroundColorSpan(Color.parseColor("#4DA0C1")),
                        0,
                        spannableHover.length,
                        SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    spannableHover.setSpan(
                        UnderlineSpan(),
                        0,
                        spannableHover.length,
                        SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    binding.txtResendOtp.text = spannableHover
                }

                MotionEvent.ACTION_UP -> {
                    // Kembalikan teks ke tampilan awal (tanpa warna biru dan underline)
                    binding.txtResendOtp.text = SpannableString("Resend again")
                    binding.txtResendOtp.performClick() // Panggil performClick untuk aksesibilitas

//                    // Navigasi ke halaman New Password
//                    val intent = Intent(this, NewPasswordActivity::class.java)
//                    startActivity(intent)
                }
            }
            true
        }

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
                        setMessage("OTP is correct")
                        setPositiveButton("Continue") { _, _ ->
                            startActivity(intentNewPasswordActivity)
                            finish()
                        }
                        create()
                        show()
                    }
                } else {
                    AlertDialog.Builder(this).apply {
                        setTitle("Oops!")
                        setMessage("Incorrect OTP")
                        setPositiveButton("Retry", null)
                        create()
                        show()
                    }
                }
            } else {
                AlertDialog.Builder(this).apply {
                    setTitle("Oops!")
                    setMessage("Incorrect OTP")
                    setPositiveButton("Retry", null)
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
                            setPositiveButton("Continue", null)
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