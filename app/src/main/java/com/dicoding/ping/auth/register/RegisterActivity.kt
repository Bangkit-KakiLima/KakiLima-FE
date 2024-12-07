package com.dicoding.ping.auth.register

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
import android.util.Patterns
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ping.api.RetrofitClient
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = AuthRepository.getInstance(RetrofitClient.apiService)
        sessionManager = SessionManager(this)

        val loginTextView = binding.txtLogin

        // Membuat SpannableString untuk teks
        val spannableStringSignUp = SpannableString("Log In")
        loginTextView.text = spannableStringSignUp

        binding.txtLogin.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {

                    // Ubah warna teks menjadi warna custom (#4DA0C1) dan tambahkan underline
                    val spannableHover = SpannableString("Log In")
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
                    binding.txtLogin.text = spannableHover
                }
                MotionEvent.ACTION_UP -> {
                    // Kembalikan teks ke tampilan awal (tanpa warna biru dan underline)
                    binding.txtLogin.text = SpannableString("Log In")
                    binding.txtLogin.performClick() // Panggil performClick untuk aksesibilitas

                    // Navigasi ke halaman Login
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        setupView()
        setupAction()
        setupPasswordValidation()
        setupEmailValidation()
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
                    binding.btnRegister.showLoading(true)
                    registerModel.register(username, email, password, role) { success ->
                        binding.btnRegister.showLoading(false)
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

    private fun setupEmailValidation() {
        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && !Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                    binding.etEmail.error = "Invalid email format"
                } else {
                    binding.etEmail.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupPasswordValidation() {
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.length < 8) {
                    binding.etPassword.error = "Password cannot be less than 8 characters"
                } else {
                    binding.etPassword.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
