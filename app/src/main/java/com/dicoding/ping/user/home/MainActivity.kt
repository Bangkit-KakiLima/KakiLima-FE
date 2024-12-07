package com.dicoding.ping.user.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.ping.R
import com.dicoding.ping.api.RetrofitClient
import com.dicoding.ping.api.RetrofitClient.apiService
import com.dicoding.ping.auth.login.LoginActivity
import com.dicoding.ping.databinding.ActivityMainBinding
import com.dicoding.ping.ui.LoadingActivity
import com.dicoding.ping.user.UserModel
import com.dicoding.ping.user.UserModelFactory
import com.dicoding.ping.user.UserRepository
import com.dicoding.ping.user.home.kategori.KategoriMakananActivity
import com.dicoding.ping.user.home.kategori.KategoriMinumanActivity
import com.dicoding.ping.user.home.product.AllProductAdapter
import com.dicoding.ping.user.home.product.ProductRecomendationAdapter
import com.dicoding.ping.utils.Helper
import com.dicoding.ping.utils.SessionManager
import com.dicoding.ping.user.home.product.ProductRepository
import com.dicoding.ping.user.home.product.ProductViewModelFactory
import com.dicoding.ping.user.locations.LokasiActivity
import com.dicoding.ping.banner.BannerFactory
import com.dicoding.ping.banner.BannerModel
import com.dicoding.ping.banner.BannerRepository
import com.dicoding.ping.banner.Weather
import com.dicoding.ping.user.home.product.ProductModel
import com.dicoding.ping.user.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private var helper: Helper = Helper()

    private lateinit var binding: ActivityMainBinding
    private lateinit var userRepository: UserRepository
    private lateinit var sessionManager: SessionManager

    private val category = listOf("Hujan", "Mendung", "Panas", "Cerah")
    private val weatherData = listOf(
        Weather(
            id = 1,
            category = "Hujan",
            description = "Cuaca hujan bikin suasana lebih nyaman, ayo lengkapi harimu dengan yang terbaik! Jangan sampai terlewat.",
            imageUrl = "https://i.pinimg.com/736x/7a/44/19/7a44199aff3fd42c45b1807feb518fa4.jpg"
        ),
        Weather(
            id = 2,
            category = "Mendung",
            description = "Langit mendung, tapi semangat tetap harus cerah! Buat harimu lebih seru dengan hal istimewa ini!",
            imageUrl = "https://i.pinimg.com/736x/28/85/71/28857188f7a6757d5dba9d3f339f1bec.jpg"
        ),
        Weather(
            id = 3,
            category = "Panas",
            description = "Cuaca panas? Waktunya cari sesuatu yang bikin segar dan nyaman. Yuk, jangan tunggu lama-lama!",
            imageUrl = "https://i.pinimg.com/736x/b9/39/79/b939794266cf899fbbd55435efd2a402.jpg"
        ),
        Weather(
            id = 4,
            category = "Cerah",
            description = "Matahari bersinar terang, saatnya menikmati hari dengan penuh semangat. Temukan pilihan yang bikin harimu lebih spesial!",
            imageUrl = "https://i.pinimg.com/736x/ff/08/a6/ff08a64470b936483bc51b823cb726eb.jpg"
        )
    )

    private val userModel: UserModel by viewModels {
        UserModelFactory(userRepository)
    }

    private val productViewModel: ProductModel by viewModels {
        ProductViewModelFactory(ProductRepository(apiService))
    }

    private val bannerModel: BannerModel by viewModels {
        BannerFactory(BannerRepository(apiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RetrofitClient with application context
        RetrofitClient.initialize(applicationContext)

        // Initialize the sessionManager and userRepository properties
        sessionManager = SessionManager(this)
        userRepository = UserRepository.getInstance(apiService)

        userModel.setSessionManager(sessionManager)

        if (!sessionManager.getIsLogin()) {
            navigateToLogin()
        } else {
            val isHorizontal = true
            productRecommendationAdapter(true)
            allProductAdapter()
            setupViewBanner()
            setupAction()
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.home

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

        binding.btnFood.setOnClickListener {
            navigateWithLoading(KategoriMakananActivity::class.java)
        }

        binding.btnDrink.setOnClickListener {
            navigateWithLoading(KategoriMinumanActivity::class.java)
        }
    }

    private fun setupAction() {
        binding.txtName.text = sessionManager.getUsername()

        binding.button.setOnClickListener {
            userModel.logout()
            navigateToLogin()
        }

        productViewModel.fetchAllProducts()
        productViewModel.fetchAllProductsRecommendations()
    }

    private fun navigateToLogin() {
        navigateWithLoading(LoginActivity::class.java)
    }

    private fun productRecommendationAdapter(isHorizontal: Boolean) {
        val productAdapter = ProductRecomendationAdapter(
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

        productViewModel.productsRecommendations.observe(this) { productList ->
            productList?.let {
                productAdapter.updateData(it)
            } ?: run {
                Log.d("MainActivity", "Product list is null or empty")
            }
        }
    }

    private fun allProductAdapter() {
        val allProductAdapter = AllProductAdapter(
            events = listOf(),
            onItemClick = { dataItem ->
                Log.d(
                    "MainActivity",
                    "Clicked item: ${dataItem.image?.let { helper.removePath(it) }}"
                )
                Toast.makeText(this, "Clicked: ${dataItem.name}", Toast.LENGTH_SHORT).show()
            }
        )

        // Menggunakan StaggeredGridLayoutManager
        val layoutManager = StaggeredGridLayoutManager(
            2, // Jumlah kolom
            StaggeredGridLayoutManager.VERTICAL // Orientasi vertikal
        )

        binding.rvAllProduct.apply {
            this.layoutManager = layoutManager
            adapter = allProductAdapter
            setHasFixedSize(true)
        }

        productViewModel.products.observe(this) { productList ->
            productList?.let {
                allProductAdapter.updateData(it)
            } ?: run {
                Log.d("MainActivity", "Product list is null or empty")
            }
        }
    }


    //    fun setupViewBaner()
    private fun setupViewBanner(){
        val handler = android.os.Handler()
        val runnable = object : Runnable {
            override fun run() {
                val category = category.random()
                weatherData.forEach {
                    if (it.category == category) {
                        Glide.with(this@MainActivity).load(it.imageUrl).into(binding.imgBackground)
                        binding.txtWeatherMessage.text = it.description
                    }
                }
                handler.postDelayed(this, 5000) // Interval 5 detik
            }
        }
        handler.post(runnable)
    }

    private fun navigateWithLoading(targetActivity: Class<*>) {
        binding.loading.visibility = View.VISIBLE // Tampilkan ProgressBar
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, targetActivity))
            binding.loading.visibility = View.GONE // Sembunyikan ProgressBar
        }, 1500)
    }

//    private fun navigateWithLoading(targetActivity: Class<*>) {
//        val targetIntent = Intent(this, targetActivity)
//        val loadingIntent = Intent(this, LoadingActivity::class.java).apply {
//            putExtra("target_intent", targetIntent)
//        }
//        startActivity(loadingIntent)
//    }
}