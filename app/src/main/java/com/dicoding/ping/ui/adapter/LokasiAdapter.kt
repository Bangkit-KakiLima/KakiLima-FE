package com.dicoding.ping.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ping.R
import com.dicoding.ping.model.Lokasi

class LokasiAdapter(private val lokasiList: ArrayList<Lokasi>) :
    RecyclerView.Adapter<LokasiAdapter.LokasiViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LokasiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lokasi, parent, false)
        return LokasiViewHolder(view)
    }

    override fun onBindViewHolder(holder: LokasiViewHolder, position: Int) {
        val lokasi = lokasiList[position]
        holder.nameTextView.text = lokasi.name
        holder.distanceTextView.text = lokasi.distance
        holder.timeTextView.text = lokasi.time
        holder.imageView.setImageResource(lokasi.imageResource)
        holder.ratingBar.rating = lokasi.rating.toFloat()
    }

    override fun getItemCount(): Int {
        return lokasiList.size
    }

    class LokasiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTextView: TextView =
            itemView.findViewById(R.id.text_name)
        var distanceTextView: TextView =
            itemView.findViewById(R.id.text_distance)
        var timeTextView: TextView =
            itemView.findViewById(R.id.text_time)
        var imageView: ImageView =
            itemView.findViewById(R.id.image_location)
        var ratingBar: RatingBar = itemView.findViewById(R.id.rating_location)
    }
}