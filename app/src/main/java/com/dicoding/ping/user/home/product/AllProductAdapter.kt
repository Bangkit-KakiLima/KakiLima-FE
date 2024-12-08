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

class AllProductAdapter(
    private var events: List<DataItem>,
    private val onItemClick: (DataItem) -> Unit
) : RecyclerView.Adapter<AllProductAdapter.ProductViewHolder>() {
    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var helper: Helper = Helper()
        val productImage: ImageView = itemView.findViewById(R.id.imageLogo)
        val productName: TextView = itemView.findViewById(R.id.namaDagangan)
        val productStatus: TextView = itemView.findViewById(R.id.status)

        fun bind(event: DataItem, onItemClick: (DataItem) -> Unit) {
            productName.text = event.name ?: "Unnamed Event"
            productStatus.text = event.merchant?.status ?: "Unknown Status"

            Glide.with(itemView.context)
                .load(event.image?.let { helper.removePath(it) })
                .into(productImage)
            itemView.setOnClickListener { onItemClick(event) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_all_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(events[position], onItemClick)
    }

    override fun getItemCount(): Int = events.size

    fun updateData(newEvents: List<DataItem>) {
        events = newEvents
        notifyDataSetChanged()
    }
}

