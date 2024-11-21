package com.example.parkirkuy.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.parkirkuy.R
import com.example.parkirkuy.ui.DonutChartView

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_parkir)

        // Ambil data dari Intent
        val namaParkir = intent.getStringExtra("NAMA_PARKIR") ?: "Tidak diketahui"
        val kapasitasParkir = intent.getStringExtra("KAPASITAS_PARKIR") ?: "0/0"
        val lokasiParkir = intent.getStringExtra("LOKASI_PARKIR") ?: "Tidak tersedia"

        // Temukan elemen UI
        val tvNamaParkir: TextView = findViewById(R.id.namaparkiran)
        val tvKapasitasParkir: TextView = findViewById(R.id.kapasitas)
        val tvLokasiParkir: TextView = findViewById(R.id.lokasiparkiran)
        val donutChart: DonutChartView = findViewById(R.id.donutChart)

        val latitude = intent.getDoubleExtra("LAT", 0.0)
        val longitude = intent.getDoubleExtra("LNG", 0.0)


        // Set data pada UI
        tvNamaParkir.text = namaParkir
        tvKapasitasParkir.text = "Kapasitas: $kapasitasParkir"
        tvLokasiParkir.text = "Lokasi: $lokasiParkir"

        // Hitung kapasitas parkir
        val totalCapacity = kapasitasParkir.split("/").getOrNull(1)?.toIntOrNull() ?: 100
        val currentCapacity = kapasitasParkir.split("/").getOrNull(0)?.toIntOrNull() ?: 0

        // Update Donut Chart
        donutChart.setChartData(currentCapacity, totalCapacity)
    }
}
