package com.dicoding.ping.user.home.kategori.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.ping.R
import com.dicoding.ping.api.RetrofitClient
import com.dicoding.ping.user.home.product.DataItem

class CategoryAdapter(
    private val productList: List<DataItem>,
    private val onItemClick: (DataItem) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.MakananViewHolder>() {

    inner class MakananViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.item_name)
        val price: TextView = itemView.findViewById(R.id.item_price)
        val image: ImageView = itemView.findViewById(R.id.item_image)
        val status: TextView = itemView.findViewById(R.id.item_status)
        val description: TextView = itemView.findViewById(R.id.item_description)


        // Bind data to views and set up the onClick listener
        fun bind(product: DataItem) {
            name.text = product.name
            description.text = product.description
            if (product.merchant?.status == "tutup") {
                status.text = "Closed"
                status.setTextColor(itemView.context.resources.getColor(R.color.red))
            } else {
                status.text = "Open"
                status.setTextColor(itemView.context.resources.getColor(R.color.green))
            }
            // Construct the full image URL
            val fullImageUrl = "${RetrofitClient.getBaseIp()}/images/products/${product.image}"
            Log.d("CategoryAdapter", "bind: $fullImageUrl")
            Log.d("CategoryAdapter", "bind: ${product.image}")
            Glide.with(itemView.context)
                .load(fullImageUrl)
                .into(image)

            // Set the onClick listener to call the onItemClick function
            itemView.setOnClickListener {
                onItemClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MakananViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kategori, parent, false)
        return MakananViewHolder(view)
    }

    override fun onBindViewHolder(holder: MakananViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product) // Bind the product to the ViewHolder
    }

    override fun getItemCount(): Int = productList.size
}

