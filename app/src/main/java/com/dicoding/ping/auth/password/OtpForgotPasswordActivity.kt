package com.dicoding.ping.auth.password

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.KeyEvent
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
                }
            }
            true
        }

        setupAction()
        setupOtpInputs()
    }

    private fun setupAction() {
        binding.btnVerify.setOnClickListener {
            val otpCode = "${binding.etBox1.text}${binding.etBox2.text}${binding.etBox3.text}" +
                    "${binding.etBox4.text}${binding.etBox5.text}${binding.etBox6.text}"

            if (otpCode.isEmpty()) {
                showAlertDialog("Oops!", "Please enter OTP.", false)
                return@setOnClickListener
            }

            val expectedOtp = sessionManager.getOtpForgotPassword()
            binding.btnVerify.showLoading(true)

            binding.btnVerify.postDelayed({
                if (otpCode == expectedOtp) {
                    showAlertDialog("Yeah!", "OTP is correct", true) {
                        binding.btnVerify.showLoading(false)
                        startActivity(Intent(this, NewPasswordActivity::class.java))
                        finish()
                    }
                } else {
                    showAlertDialog("Oops!", "Incorrect OTP", false)
                    binding.btnVerify.showLoading(false)
                }
            }, 2000)
        }

        binding.txtResendOtp.setOnClickListener {
            val email = sessionManager.getEmailForgotPassword()
            if (email.isNullOrEmpty()) {
                showAlertDialog("Error", "Email is missing. Please try again.", false)
                return@setOnClickListener
            }

            binding.txtResendOtp.isEnabled = false
            binding.btnVerify.showLoading(true)

            otpModel.resendOtpForgotPassword(email) { success ->
                binding.btnVerify.showLoading(false) // Hilangkan loading setelah selesai
                binding.txtResendOtp.isEnabled = true

                val title = if (success) "OTP Sent" else "Error"
                val message = if (success) {
                    "OTP has been resent to $email."
                } else {
                    "Failed to resend OTP. Please try again."
                }
                showAlertDialog(title, message, false)
            }
        }
    }

    fun setupOtpInputs() {
        val otpBoxes = listOf(
            binding.etBox1,
            binding.etBox2,
            binding.etBox3,
            binding.etBox4,
            binding.etBox5,
            binding.etBox6
        )

        otpBoxes.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (!s.isNullOrEmpty() && index < otpBoxes.size - 1) {
                        otpBoxes[index + 1].requestFocus()
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            editText.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_DEL &&
                    editText.text.isEmpty() &&
                    index > 0
                ) {
                    otpBoxes[index - 1].requestFocus()
                }
                false
            }
        }
    }

    private fun showAlertDialog(
        title: String,
        message: String,
        isSuccess: Boolean,
        onContinue: (() -> Unit)? = null
    ) {
        binding.btnVerify.showLoading(false) // Pastikan loading dihentikan sebelum dialog
        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(if (isSuccess) "Continue" else "Retry") { _, _ -> onContinue?.invoke() }
            create()
            show()
        }
    }
}