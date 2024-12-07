//package com.dicoding.ping.user.home.product
//
//import android.content.Context
//import android.content.Intent
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.dicoding.ping.R
//
//class ProductAdapter(private val context: Context, private val products: List<ProductDetail>) :
//    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
//
//    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val nameTextView: TextView = itemView.findViewById(R.id.product_name)
//        val imageView: ImageView = itemView.findViewById(R.id.product_image)
//
//        init {
//            itemView.setOnClickListener {
//                val product = products[adapterPosition]
//                val intent = Intent(context, DetailProductActivity::class.java)
//                // Mengirim seluruh objek ProductDetail ke DetailProductActivity
//                intent.putExtra("product_detail", product)
//                context.startActivity(intent)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
//        val itemView = LayoutInflater.from(context).inflate(R.layout.item_recommendation, parent, false)
//        return ProductViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
//        val product = products[position]
//        holder.nameTextView.text = product.name
//        Glide.with(context)
//            .load(product.imageUrl)
//            .into(holder.imageView)
//    }
//
//    override fun getItemCount(): Int {
//        return products.size
//    }
//}
//
//private fun Intent.putExtra(s: String, product: ProductDetail) {
//
//}
