package com.example.parkirkuy.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.parkirkuy.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo: ImageView = findViewById(R.id.iv_logo)
        val appName: TextView = findViewById(R.id.tv_app_name)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        val zoomInAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.zoom_in)
        val slideUpAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        val fadeInAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        logo.startAnimation(zoomInAnim)
        appName.startAnimation(slideUpAnim)

        progressBar.visibility = ProgressBar.VISIBLE
        progressBar.startAnimation(fadeInAnim)

        Handler(Looper.getMainLooper()).postDelayed({
            goToMainActivity()
        }, 3000L)
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
