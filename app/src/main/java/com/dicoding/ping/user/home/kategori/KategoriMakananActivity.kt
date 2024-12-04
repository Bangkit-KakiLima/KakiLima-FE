package com.dicoding.ping.user.home.kategori

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ping.R
import com.dicoding.ping.user.home.kategori.model.Makanan
import com.dicoding.ping.user.home.kategori.adapter.MakananAdapter

class KategoriMakananActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategori_makanan)

        recyclerView = findViewById(R.id.recycler_view_kategori_food)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val makananList = listOf(
            Makanan("Fried Rice", "Delicious fried rice with egg", "Rp 20.000",
                R.drawable.ic_launcher_background
            ),
            Makanan("Fried Noodles", "Fried noodles with vegetables", "Rp 18.000",
                R.drawable.ic_launcher_background
            )
        )

        val adapter = MakananAdapter(makananList)
        recyclerView.adapter = adapter
    }
}
