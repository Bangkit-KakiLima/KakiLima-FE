package com.dicoding.ping.banner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.ping.R

class BannerAdapter(private val weatherList: List<Weather>) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    inner class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageWeather: ImageView = view.findViewById(R.id.overlay)
        val textDescription: TextView = view.findViewById(R.id.fragment_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_main, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val weather = weatherList[position]
        holder.textDescription.text = weather.description
        Glide.with(holder.itemView.context)
            .load(weather.imageUrl)
            .into(holder.imageWeather)
    }

    override fun getItemCount(): Int = weatherList.size
}