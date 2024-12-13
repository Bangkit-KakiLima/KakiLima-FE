package com.dicoding.ping.user.home.kategori

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ping.R
import com.dicoding.ping.api.RetrofitClient
import com.dicoding.ping.user.home.kategori.adapter.CategoryAdapter
import com.dicoding.ping.user.home.product.DetailProductActivity
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
                val adapter = CategoryAdapter(products, onItemClick = { dataItem ->
                    //berpindah ke halaman detail product
                    val intent = Intent(this, DetailProductActivity::class.java)
                    intent.putExtra("id", dataItem.id)
                    startActivity(intent)
                    Log.d("KategoriMakananActivity", "onCreate: cliced")
                })
                recyclerView.adapter = adapter
            } else {
                Toast.makeText(this, "Gagal memuat data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

