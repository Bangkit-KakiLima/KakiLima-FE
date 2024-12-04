package com.dicoding.ping.user.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ping.R
import com.dicoding.ping.api.RetrofitClient
import com.dicoding.ping.api.RetrofitClient.apiService
import com.dicoding.ping.auth.login.LoginActivity
import com.dicoding.ping.databinding.ActivityMainBinding
import com.dicoding.ping.ui.LokasiActivity
import com.dicoding.ping.user.UserModel
import com.dicoding.ping.user.UserModelFactory
import com.dicoding.ping.user.UserRepository
import com.dicoding.ping.user.home.product.ProductAdapter
import com.dicoding.ping.user.profile.ProfileActivity
import com.dicoding.ping.utils.Helper
import com.dicoding.ping.utils.SessionManager
import com.dicoding.ping.user.home.product.ProductModel
import com.dicoding.ping.user.home.product.ProductRepository
import com.dicoding.ping.user.home.product.ProductViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private var helper: Helper = Helper()
    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: UserRepository
    private lateinit var sessionManager: SessionManager
    private val userModel: UserModel by viewModels {
        UserModelFactory(repository)
    }
    private val productViewModel: ProductModel by viewModels {
        ProductViewModelFactory(ProductRepository(apiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = UserRepository.getInstance(RetrofitClient.apiService)
        sessionManager = SessionManager(this)
        userModel.setSessionManager(sessionManager)

        if (!sessionManager.getIsLogin()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            setupAction()
        }

        // Setup BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set the default selected menu to "Home"
        bottomNavigationView.selectedItemId = R.id.home

        // Listener untuk navigasi
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.location -> {
                    val intent = Intent(this, LokasiActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

    }

    private fun setupAction() {
        binding.txtName.text = sessionManager.getUsername()
        binding.button.setOnClickListener {
            userModel.logout()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        productViewModel.fetchAllProducts()
    }

    private fun setupRecyclerView(isHorizontal: Boolean) {
        val productAdapter = ProductAdapter(
            events = listOf(),
            onItemClick = { dataItem ->
                Log.d(
                    "MainActivity",
                    "Clicked item: ${dataItem.image?.let { helper.removePath(it) }}"
                )
                Toast.makeText(this, "Clicked: ${dataItem.name}", Toast.LENGTH_SHORT).show()
            }
        )

        val layoutManager = if (isHorizontal) {
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        } else {
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }

        binding.rvRecommendations.apply {
            this.layoutManager = layoutManager
            adapter = productAdapter
            setHasFixedSize(true)
        }

        productViewModel.products.observe(this) { productList ->
            productList?.let {
                productAdapter.updateData(it)
            } ?: run {
                Log.d("MainActivity", "Product list is null or empty")
            }
        }
    }
}