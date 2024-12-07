package com.dicoding.ping.user.profile

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.ping.ui.LoadingActivity
import com.dicoding.ping.R
import com.dicoding.ping.api.RetrofitClient.apiService
import com.dicoding.ping.auth.login.LoginActivity
import com.dicoding.ping.databinding.ActivityProfileBinding
import com.dicoding.ping.user.locations.LokasiActivity
import com.dicoding.ping.user.UserModel
import com.dicoding.ping.user.UserModelFactory
import com.dicoding.ping.user.UserRepository
import com.dicoding.ping.user.home.MainActivity
import com.dicoding.ping.utils.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var userRepository: UserRepository
    private lateinit var sessionManager: SessionManager

    private val userModel: UserModel by viewModels {
        UserModelFactory(userRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)
        userRepository = UserRepository.getInstance(apiService)

        findViewById<TextView>(R.id.edit_profile).setOnClickListener {
            navigateToFragment(EditProfileFragment())
        }

        findViewById<TextView>(R.id.address).setOnClickListener {
            navigateToFragment(EditAddressFragment())
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.profile
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    navigateWithLoading(MainActivity::class.java)
                    true
                }

                R.id.location -> {
                    navigateWithLoading(LokasiActivity::class.java)
                    true
                }

                R.id.profile -> {
                    navigateWithLoading(ProfileActivity::class.java)
                    true
                }

                else -> false
            }
        }
        setupAction()
    }

    private fun setupAction() {
        userModel.setSessionManager(sessionManager) // Inisialisasi sessionManager di UserModel
        binding.logout.setOnClickListener {
            userModel.logout()
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        navigateWithLoading(LoginActivity::class.java)
        finish()
    }

    private fun navigateWithLoading(targetActivity: Class<*>) {
        Log.d("ProfileActivity", "Loading started")
        binding.loadingProfile.visibility = View.VISIBLE // Tampilkan ProgressBar
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, targetActivity))
            binding.loadingProfile.visibility = View.GONE // Sembunyikan ProgressBar
        }, 1500)
    }

    // Fungsi untuk berpindah fragment
    private fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}

