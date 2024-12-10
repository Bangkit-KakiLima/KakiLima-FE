package com.dicoding.ping.user.home.product

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.ping.R
import com.dicoding.ping.api.RetrofitClient.apiService
import com.dicoding.ping.utils.Helper

class DetailProductActivity : AppCompatActivity() {
    private var helper: Helper = Helper()
    private val productViewModel: ProductModel by viewModels {
        ProductViewModelFactory(ProductRepository(apiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)

        val lyDetailProductTop: LinearLayout = findViewById(R.id.lyDetailProductTop)
        val lyDetailProductBottom: LinearLayout = findViewById(R.id.lyDetailProductBottom)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        val txtLoadingMessage: TextView = findViewById(R.id.txtLoadingMessage)

        //membuat indikator loading
        lyDetailProductTop.visibility = View.INVISIBLE
        lyDetailProductBottom.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
        txtLoadingMessage.visibility = View.VISIBLE

        // Menerima data dari Intent
        val id = intent.getIntExtra("id", -1)
        if (id != -1) {
            Log.d(TAG, "EventId: $id")
            productViewModel.fetchProductDetail(id)
        } else {
            Log.e(TAG, "Invalid event ID")
        }

        // Menampilkan data ke dalam view
        val nameTextView: TextView = findViewById(R.id.product_name)
        val rateTextView: TextView = findViewById(R.id.product_rating)
        val descriptionTextView: TextView = findViewById(R.id.product_description)
        val priceTextView: TextView = findViewById(R.id.product_price)
        val sellerTextView: TextView = findViewById(R.id.merchant_name)
        val imageView: ImageView = findViewById(R.id.product_image)

        Handler(Looper.getMainLooper()).postDelayed({
            productViewModel.productDetailLiveData.observe(this) { productDetail ->
                productDetail?.data?.let { data ->
                    nameTextView.text = data.name ?: "Unnamed Product"
                    rateTextView.text = (data.merchant?.average_rating ?: "Unknown Rate").toString()
                    descriptionTextView.text = data.description ?: "Unknown Description"
                    priceTextView.text = data.price?.let { helper.formatRupiah(it.toInt()) }
                    sellerTextView.text = data.merchant?.business_name ?: "Unknown Seller"

                    Glide.with(this)
                        .load(data.image)
                        .into(imageView)

                    //membuat indikator loading
                    lyDetailProductBottom.visibility = View.VISIBLE
                    lyDetailProductBottom.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    txtLoadingMessage.visibility = View.GONE
                } ?: run {
                    Log.e(TAG, "Product detail is null")
                }
            }
        }, 3000)
    }
}