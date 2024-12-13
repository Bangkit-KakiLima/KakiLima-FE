package com.dicoding.ping.user.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.ping.R
import com.dicoding.ping.api.RetrofitClient
import com.dicoding.ping.api.RetrofitClient.apiService
import com.dicoding.ping.auth.login.LoginActivity
import com.dicoding.ping.banner.BannerFactory
import com.dicoding.ping.banner.BannerModel
import com.dicoding.ping.banner.BannerRepository
import com.dicoding.ping.banner.Weather
import com.dicoding.ping.banner.weather.WeatherModel
import com.dicoding.ping.banner.weather.WeatherModelFactory
import com.dicoding.ping.banner.weather.WeatherRepository
import com.dicoding.ping.databinding.ActivityMainBinding
import com.dicoding.ping.ui.ScrollingFragmentRecProduct
import com.dicoding.ping.user.UserModel
import com.dicoding.ping.user.UserModelFactory
import com.dicoding.ping.user.UserRepository
import com.dicoding.ping.user.home.kategori.KategoriMakananActivity
import com.dicoding.ping.user.home.kategori.KategoriMinumanActivity
import com.dicoding.ping.user.home.product.AllProductAdapter
import com.dicoding.ping.user.home.product.DetailProductActivity
import com.dicoding.ping.user.home.product.ProductModel
import com.dicoding.ping.user.home.product.ProductRecomendationAdapter
import com.dicoding.ping.user.home.product.ProductRepository
import com.dicoding.ping.user.home.product.ProductViewModelFactory
import com.dicoding.ping.user.locations.LokasiActivity
import com.dicoding.ping.user.profile.ProfileActivity
import com.dicoding.ping.user.profile.ProfileRepository
import com.dicoding.ping.utils.Helper
import com.dicoding.ping.utils.SessionManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var helper: Helper = Helper()

    private lateinit var binding: ActivityMainBinding
    private lateinit var userRepository: UserRepository
    private lateinit var sessionManager: SessionManager
    private var setTimer = 1000
    private val locationPermissionCode = 100
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val weatherData = listOf(
        Weather(
            id = 1,
            category = "Rain",
            description = "Rainy weather is perfect for something warm. Enjoy a selection of hot drinks and comforting meals that make the atmosphere cozy",
            imageUrl = "https://i.pinimg.com/736x/7a/44/19/7a44199aff3fd42c45b1807feb518fa4.jpg"
        ),
        Weather(
            id = 2,
            category = "Drizzle",
            description = "Light drizzle creates a soothing vibe. It’s the perfect time for warm snacks and sweet drinks to accompany your day",
            imageUrl = "file:///C:/Users/asusu/Downloads/high-angle-closeup-shot-isolated-green-leaf-puddle-rainy-day.jpg"
        ),
        Weather(
            id = 3,
            category = "Clouds",
            description = "Cloudy skies don’t have to be dull. Discover delicious food and favorite drinks that bring cheer to your day",
            imageUrl = "https://i.pinimg.com/736x/ff/08/a6/ff08a64470b936483bc51b823cb726eb.jpg"
        ),
        Weather(
            id = 4,
            category = "Clear",
            description = "Clear skies and fresh air? It’s the perfect moment to enjoy cold drinks and light snacks that make your day even better",
            imageUrl = "https://i.pinimg.com/736x/b9/39/79/b939794266cf899fbbd55435efd2a402.jpg"
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

    private val weatherModel: WeatherModel by viewModels {
        WeatherModelFactory(WeatherRepository(apiService))
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
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        } else {
            setupHome()

            val scrollingFragmentRecProduct = ScrollingFragmentRecProduct()

            binding.btnSeeAll.setOnClickListener {
                scrollingFragmentRecProduct.show(
                    supportFragmentManager,
                    "ScrollingFragmentRecProduct"
                )
            }
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

    fun setupHome() {
        val isHorizontal = true
        productRecommendationAdapter(true)
        allProductAdapter(isHorizontal)
        checkAndRequestLocationPermission()
    }


    private fun setupAction() {
        binding.txtName.text = sessionManager.getUsername()
        productViewModel.fetchAllProducts()
        productViewModel.fetchAllProductsRecommendations()
    }

    private fun productRecommendationAdapter(isHorizontal: Boolean) {
        val productAdapter = ProductRecomendationAdapter(
            events = listOf(),
            onItemClick = { dataItem ->
                //berpindah ke halaman detail product
                val intent = Intent(this, DetailProductActivity::class.java)
                intent.putExtra("id", dataItem.id)
                startActivity(intent)
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

    private fun allProductAdapter(isHorizontal: Boolean) {
        val allProductAdapter = AllProductAdapter(
            events = listOf(),
            onItemClick = { dataItem ->
                //berpindah ke halaman detail product
                val intent = Intent(this, DetailProductActivity::class.java)
                intent.putExtra("id", dataItem.id)
                startActivity(intent)
            }
        )

        val layoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
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

    private fun setupViewBaner() {
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                if (isDestroyed) return // Check if the activity is destroyed
                val weather_main = weatherModel.weather.value?.data?.weather_main.toString()
                val weather_location = sessionManager.getCityName()
                binding.txtCity.text = weather_location
                binding.txtWeather.text = weather_main

                binding.txtTemperature.text =
                    helper.roundToNearestInteger(weatherModel.weather.value?.data?.temperature.toString())
                        .toString()
                Log.d("MainActivity", "Weather: $weather_main")
                weatherData.forEach {
                    if (it.category == weather_main) {
                        Glide.with(this@MainActivity).load(it.imageUrl).into(binding.imgBackground)
                        binding.txtWeatherMessage.text = it.description
                    }
                }
                val delay =
                    if (weather_main == "null") 1000L else 10800000L // 1 second if null, 3 hour otherwise
                handler.postDelayed(this, delay)
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

    private fun checkAndRequestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        } else {
            fetchAndSaveLocation()
            setupAction()
        }
    }

    private fun fetchAndSaveLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//        untuk mengecek apakah permission sudah diberikan atau belum
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val latitude = location.latitude.toString()
                val longitude = location.longitude.toString()
                val profileRepository = ProfileRepository(apiService, this)
                lifecycleScope.launch {
                    profileRepository.checkAndSaveAddress(latitude, longitude)
                }
                getCityFromCoordinates(location.latitude, location.longitude)
                getFullAddressFromCoordinates(location.latitude, location.longitude)
                setupViewBaner()
                Log.d("MainActivity", "Location saved: $latitude, $longitude")
            } else {
                Toast.makeText(this, "Lokasi tidak tersedia", Toast.LENGTH_SHORT).show()
            }
        }
        weatherModel.fetchDataWeather()
    }

    fun getCityFromCoordinates(latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: MutableList<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                sessionManager.saveCityName(address.locality)
                Log.i("", "getCityFromCoordinates: " + address.locality)
                return address.locality ?: "Indonesia"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "Indonesia"
    }

    fun getFullAddressFromCoordinates(latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: MutableList<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                val fullAddress = address.getAddressLine(0).substringAfter(" ")
                sessionManager.saveFullAddress(fullAddress)
                Log.i("MainActivity", "getFullAddressFromCoordinates: $fullAddress")
                return fullAddress ?: "Indonesia"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "Indonesia"
    }

}