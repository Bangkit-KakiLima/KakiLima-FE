package com.dicoding.ping.kategori

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ping.R

class KategoriActivity : AppCompatActivity() {

    private lateinit var foodList: RecyclerView
    private lateinit var drinkList: RecyclerView
    private lateinit var kategoriAdapter: KategoriAdapter
    private val kategoriList = ArrayList<Kategori>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategori_makanan)
        setContentView(R.layout.activity_kategori_minuman)


        foodList = findViewById(R.id.recycler_view_kategori_food)
        drinkList = findViewById(R.id.recycler_view_kategori_food)

        kategoriList.add(
            Kategori(
                imageResource = R.drawable.ic_food,
                name = "Food",
                rating = 4.5,
                distance = "2 km",
                price = "$10",
                isOpen = true
            )
        )
        kategoriList.add(
            Kategori(
                imageResource = R.drawable.ic_drink,
                name = "Drink",
                rating = 4.0,
                distance = "1.5 km",
                price = "$5",
                isOpen = false
            )
        )

        kategoriAdapter = KategoriAdapter(kategoriList)

        foodList.adapter = kategoriAdapter
        foodList.layoutManager = LinearLayoutManager(this)

        // Atur RecyclerView untuk daftar makanan / minuman
        drinkList.adapter = kategoriAdapter
        drinkList.layoutManager = LinearLayoutManager(this)

        foodList.adapter = kategoriAdapter
        foodList.layoutManager = LinearLayoutManager(this)

    }
}
