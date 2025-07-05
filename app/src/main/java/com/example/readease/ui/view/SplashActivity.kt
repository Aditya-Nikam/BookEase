package com.example.readease.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.readease.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val splashDelay: Long = 3000  // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        val splashGif = findViewById<ImageView>(R.id.splashGif)

        // Load GIF using Glide
        Glide.with(this)
            .asGif()
            .load(R.drawable.logo)
            .into(splashGif)

        // Delay and navigate
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, splashDelay)
    }
}