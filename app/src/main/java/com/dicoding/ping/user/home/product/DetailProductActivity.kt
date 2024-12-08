package com.dicoding.ping.user.home.product

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.ping.R

class DetailProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)

        val productDetail: ProductDetail = intent.getParcelableExtra("product_detail")!!
        val nameTextView: TextView = findViewById(R.id.product_name)
        val rateTextView: TextView = findViewById(R.id.product_rating)
        val descriptionTextView: TextView = findViewById(R.id.product_description)
        val priceTextView: TextView = findViewById(R.id.product_price)
        val sellerTextView: TextView = findViewById(R.id.merchant_name)
        val imageView: ImageView = findViewById(R.id.product_image)

        nameTextView.text = productDetail.name
        rateTextView.text = productDetail.rate
        descriptionTextView.text = productDetail.description
        priceTextView.text = "Rp ${productDetail.price}"
        sellerTextView.text = "Seller: ${productDetail.seller}"

        Glide.with(this)
            .load(productDetail.imageUrl)
            .into(imageView)
    }
}
