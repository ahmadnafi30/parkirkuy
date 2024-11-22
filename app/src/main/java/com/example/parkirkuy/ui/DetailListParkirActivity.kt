package com.example.parkirkuy.ui

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.parkirkuy.R

class DetailListParkirActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var lokasiTextView: TextView
    private lateinit var tanggalTextView: TextView
    private lateinit var fotoImageView: ImageView

    // DetailListParkirActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailreservasi2)

        backButton = findViewById(R.id.backbutton)
        lokasiTextView = findViewById(R.id.namafilkom)
        tanggalTextView = findViewById(R.id.tanggalreservasidetail)
        fotoImageView = findViewById(R.id.fototempatparkiran)

        val jamTextView: TextView = findViewById(R.id.jamreservasidetail)
        val durasiTextView: TextView = findViewById(R.id.durasireservasidetail)
        val statusTextView: TextView = findViewById(R.id.statusparkir)

        // Handle back button
        backButton.setOnClickListener { finish() }

        // Terima data dari Intent
        val reservasi = intent.getSerializableExtra("EXTRA_RESERVASI") as? Reservasi
        reservasi?.let {
            lokasiTextView.text = it.lokasi
            tanggalTextView.text = it.tanggal
            fotoImageView.setImageResource(it.foto)
            statusTextView.text = it.status

            // Contoh jam dan durasi dummy
            jamTextView.text = "12:20 - 15:00"
            durasiTextView.text = "2 jam 40 menit"
        }
    }

}
