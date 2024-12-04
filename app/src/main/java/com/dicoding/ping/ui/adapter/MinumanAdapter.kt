package com.dicoding.ping.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ping.model.Minuman
import com.dicoding.ping.R

class MinumanAdapter(private val minumanList: List<Minuman>) :
    RecyclerView.Adapter<MinumanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kategori, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val minuman = minumanList[position]
        holder.itemImage.setImageResource(minuman.imageResource)
        holder.itemName.text = minuman.name
        holder.itemDescription.text = minuman.description
        holder.itemPrice.text = minuman.price
    }

    override fun getItemCount(): Int {
        return minumanList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.item_image)
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val itemDescription: TextView = itemView.findViewById(R.id.item_description)
        val itemPrice: TextView = itemView.findViewById(R.id.item_price)
    }
}
