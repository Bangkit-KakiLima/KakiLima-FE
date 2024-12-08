package com.dicoding.ping.kategori

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ping.R
import com.dicoding.ping.api.RetrofitClient
import com.dicoding.ping.ui.adapter.CategoryAdapter
import com.dicoding.ping.user.home.product.ProductModel
import com.dicoding.ping.user.home.product.ProductRepository
import com.dicoding.ping.user.home.product.ProductViewModelFactory

class KategoriMakananActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ProductModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategori_makanan)
        recyclerView = findViewById(R.id.recycler_view_kategori_food)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val apiService = RetrofitClient.apiService
        val repository = ProductRepository(apiService)
        viewModel = ViewModelProvider(this, ProductViewModelFactory(repository))[ProductModel::class.java]
        viewModel.fetchProductsByCategory("Makanan")
        viewModel.categoryProducts.observe(this) { products ->
            if (products != null) {
                val adapter = CategoryAdapter(products)
                recyclerView.adapter = adapter
            } else {
                Toast.makeText(this, "Gagal memuat data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
