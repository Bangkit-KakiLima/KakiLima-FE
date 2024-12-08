package com.dicoding.ping.user.profile

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.ping.R
import com.dicoding.ping.api.RetrofitClient
import com.dicoding.ping.auth.login.LoginActivity
import com.dicoding.ping.databinding.ActivityProfileBinding
import com.dicoding.ping.user.UserModel
import com.dicoding.ping.user.UserModelFactory
import com.dicoding.ping.user.UserRepository
import com.dicoding.ping.user.home.MainActivity
import com.dicoding.ping.user.locations.LokasiActivity
import com.dicoding.ping.user.profile.address.EditAddressFragment
import com.dicoding.ping.utils.SessionManager

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var userRepository: UserRepository
    private lateinit var sessionManager: SessionManager

    private val userModel: UserModel by lazy {
        val apiService = RetrofitClient.apiService
        userRepository = UserRepository(apiService)

        val viewModelFactory = UserModelFactory(userRepository)
        viewModelFactory.create(UserModel::class.java).apply {
            setSessionManager(SessionManager(this@ProfileActivity))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)

        binding.profileName.text = sessionManager.getUsername()
        binding.editProfile.setOnClickListener {
            navigateToFragment(EditProfileFragment())
        }

        binding.address.setOnClickListener {
            navigateToFragment(EditAddressFragment())
        }

        // Navigasi menggunakan bottom navigation
        val bottomNavigationView = binding.bottomNavigation
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
        binding.loadingProfile.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, targetActivity))
            binding.loadingProfile.visibility = View.GONE
        }, 1500)
    }

    private fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
