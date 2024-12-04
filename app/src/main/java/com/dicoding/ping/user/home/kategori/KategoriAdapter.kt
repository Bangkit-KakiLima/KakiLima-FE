package com.dicoding.ping.user.home.kategori

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ping.R

class KategoriAdapter(private val kategoriList: ArrayList<Kategori>) :
    RecyclerView.Adapter<KategoriAdapter.ViewHolder>() {

    // Membuat ViewHolder untuk item kategori
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kategori, parent, false)
        return ViewHolder(view)
    }

    // Mengikat data ke ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kategori = kategoriList[position]

        // Menetapkan data ke view-item sesuai dengan data kategori
        holder.itemImage.setImageResource(kategori.imageResource)
        holder.itemName.text = kategori.name
        holder.itemRating.text = kategori.rating.toString()
        holder.itemDistance.text = kategori.distance
        holder.itemPrice.text = kategori.price
        holder.itemStatus.text = if (kategori.isOpen) "Buka" else "Tutup"
    }

    // Mengembalikan jumlah item
    override fun getItemCount(): Int {
        return kategoriList.size
    }

    // ViewHolder untuk item kategori
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.item_image)
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val itemRating: TextView = itemView.findViewById(R.id.item_rating)
        val itemDistance: TextView = itemView.findViewById(R.id.item_distance)
        val itemPrice: TextView = itemView.findViewById(R.id.item_price)
        val itemStatus: TextView = itemView.findViewById(R.id.item_status)
    }
}
