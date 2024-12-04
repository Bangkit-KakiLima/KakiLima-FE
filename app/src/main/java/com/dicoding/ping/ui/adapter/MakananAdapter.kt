package com.dicoding.ping.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ping.R
import com.dicoding.ping.model.Makanan

class MakananAdapter(private val makananList: List<Makanan>) :
    RecyclerView.Adapter<MakananAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kategori, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val makanan = makananList[position]
        holder.itemImage.setImageResource(makanan.imageResource)
        holder.itemName.text = makanan.name
        holder.itemDescription.text = makanan.description
        holder.itemPrice.text = makanan.price
    }

    override fun getItemCount(): Int {
        return makananList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.item_image)
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val itemDescription: TextView = itemView.findViewById(R.id.item_description)
        val itemPrice: TextView = itemView.findViewById(R.id.item_price)
    }
}
