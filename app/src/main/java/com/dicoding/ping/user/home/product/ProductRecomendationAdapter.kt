package com.dicoding.ping.user.home.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.ping.R
import com.dicoding.ping.utils.Helper

class ProductRecomendationAdapter(private var events: List<DataItem>, private val onItemClick: (DataItem) -> Unit) : RecyclerView.Adapter<ProductRecomendationAdapter.ProductViewHolder>() {
    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var helper: Helper = Helper()
        val productImage: ImageView = itemView.findViewById(R.id.imageLogo)
        val productName: TextView = itemView.findViewById(R.id.namaDagangan)
        val productPrice: TextView = itemView.findViewById(R.id.harga)
        val productRating: TextView = itemView.findViewById(R.id.rating)
        val productStatus: TextView = itemView.findViewById(R.id.status)

        fun bind(event: DataItem, onItemClick: (DataItem) -> Unit) {
            productName.text = event.name ?: "Unnamed Event"
            productPrice.text = event.price ?: "Unknown Price"
            productRating.text = (event.merchant?.average_rating ?: "Unknown Rating").toString()
            productStatus.text = event.merchant?.status ?: "Unknown Status"
            if(event.merchant?.status == "tutup") {
                productStatus.setTextColor(itemView.context.resources.getColor(R.color.red))
            } else {
                productStatus.setTextColor(itemView.context.resources.getColor(R.color.green))
            }

            Glide.with(itemView.context)
                .load(event.image?.let { helper.removePath(it) })
                .into(productImage)
            itemView.setOnClickListener { onItemClick(event) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recommendation, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(events[position], onItemClick)
    }

    override fun getItemCount(): Int = events.size

    // fungsi untuk memperbarui data adapter
    fun updateData(newEvents: List<DataItem>) {
        events = newEvents
        notifyDataSetChanged()
    }
}

