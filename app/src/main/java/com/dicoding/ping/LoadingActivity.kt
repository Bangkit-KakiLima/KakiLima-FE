package com.dicoding.ping

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.util.Log

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        // Ambil intent halaman tujuan dari extra
        val targetIntent = intent.getParcelableExtra<Intent>("target_intent")

        // Pastikan hanya ada satu aktivitas loading yang berjalan
        if (targetIntent != null) {
            // Tambahkan delay untuk melihat loading screen
            Handler(Looper.getMainLooper()).postDelayed({
                targetIntent?.let {
                    startActivity(it)
                    finish() // Menutup LoadingActivity setelah halaman tujuan dimulai
                } ?: run {
                    Log.e("LoadingActivity", "Target intent is null")
                }
            }, 1500)
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out) // Animasi transisi
    }
}
