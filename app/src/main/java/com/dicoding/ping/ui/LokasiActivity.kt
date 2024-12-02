package com.dicoding.ping.ui

import LocationsModel
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ping.R
import com.dicoding.ping.api.RetrofitClient.apiService
import com.dicoding.ping.locations.LocationResponse
import com.dicoding.ping.locations.LocationsModelFactory
import com.dicoding.ping.locations.LocationsRepository
import com.dicoding.ping.model.Lokasi
import com.dicoding.ping.ui.adapter.LokasiAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LokasiActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mapView: MapView
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: LocationsModel
    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lokasi)

        // Inisialisasi Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Inisialisasi MapView
        mapView = findViewById(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.recycler_view_places)
        recyclerView.setLayoutManager(LinearLayoutManager(this))

        // Data Dummy
        val lokasiList = ArrayList<Lokasi>()
        lokasiList.add(
            Lokasi(
                "Restoran A", "1.5 km", "15 menit", R.drawable.ic_launcher_background, 4.5, true))
        lokasiList.add(
            Lokasi("Cafe B", "2.0 km", "20 menit", R.drawable.ic_launcher_background, 4.0, false))

        val adapter = LokasiAdapter(lokasiList)
        recyclerView.setAdapter(adapter)

        val repository = LocationsRepository.getInstance(apiService)
        viewModel = ViewModelProvider(
            this,
            LocationsModelFactory(repository)
        )[LocationsModel::class.java]

        viewModel.locations.observe(this) { locations ->
            showMarkers(locations)
        }
        
        viewModel.getAllLocations()

    }

    private fun showMarkers(locations: List<LocationResponse>) {
        googleMap?.let { map ->
            for (location in locations) {
                val latLng = LatLng(location.latitude.toDouble(), location.longitude.toDouble())
                map.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(location.merchant.business_name)
                )
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val lokasiPengguna = LatLng(-6.9175, 107.6191) // Bandung
        googleMap!!.addMarker(MarkerOptions().position(lokasiPengguna).title("Lokasi Anda"))
        googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasiPengguna, 15f))
    }

    override fun onResume() {
        super.onResume()
        mapView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView!!.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView!!.onSaveInstanceState(outState)
    }
}