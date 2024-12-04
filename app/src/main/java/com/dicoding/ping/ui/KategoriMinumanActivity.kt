package com.dicoding.ping.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ping.R
import com.dicoding.ping.model.Minuman
import com.dicoding.ping.ui.adapter.MinumanAdapter

class KategoriMinumanActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategori_minuman)

        recyclerView = findViewById(R.id.recycler_view_kategori_drink)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val minumanList = listOf(
            Minuman("Iced Tea", "Fresh sweet iced tea", "Rp 5.000", R.drawable.ic_launcher_background),
            Minuman("Orange Juice", "Fresh orange juice", "Rp 10.000", R.drawable.ic_launcher_background)
        )

        val adapter = MinumanAdapter(minumanList)
        recyclerView.adapter = adapter
    }
}
