package com.dicoding.ping.user.home.kategori

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
        setContentView(R.layout.activity_kategori)

        foodList = findViewById(R.id.food_list)
        drinkList = findViewById(R.id.drink_list)

        kategoriList.add(
            Kategori(
                imageResource = R.drawable.ic_food,
                name = "Food",
                rating = 4.5,
                price = "$10",
                isOpen = true
            )
        )
        kategoriList.add(
            Kategori(
                imageResource = R.drawable.ic_drink,
                name = "Drink",
                rating = 4.0,
                price = "$5",
                isOpen = false
            )
        )

        kategoriAdapter = KategoriAdapter(kategoriList)

        foodList.adapter = kategoriAdapter
        foodList.layoutManager = LinearLayoutManager(this)

        // Atur RecyclerView untuk daftar minuman
        drinkList.adapter = kategoriAdapter
        drinkList.layoutManager = LinearLayoutManager(this)
    }
}
