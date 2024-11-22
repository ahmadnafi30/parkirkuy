package com.example.parkirkuy.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parkirkuy.R
import com.example.parkirkuy.ui.ReservasiAdapter
import com.example.parkirkuy.ui.Reservasi

class DaftarReservasiActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var reservasiRecyclerView: RecyclerView
    private lateinit var reservasiAdapter: ReservasiAdapter
    private val reservasiList = mutableListOf<Reservasi>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftarreservasi)

        // Inisialisasi View
        backButton = findViewById(R.id.backbutton)
        reservasiRecyclerView = findViewById(R.id.recyclerView_reservasi)

        // Klik tombol kembali
        backButton.setOnClickListener {
            finish()
        }

        // Setup RecyclerView
        reservasiAdapter = ReservasiAdapter(reservasiList) { reservasi ->
            navigateToDetail(reservasi)
        }

        reservasiRecyclerView.layoutManager = LinearLayoutManager(this)
        reservasiRecyclerView.adapter = reservasiAdapter

        // Data Dummy untuk testing
        loadDummyData()
    }

    private fun loadDummyData() {
        reservasiList.add(Reservasi("15 Oktober 2024", "Parkiran FILKOM A", "Aktif", R.drawable.fotoparkir))
        reservasiList.add(Reservasi("16 Oktober 2024", "Parkiran FILKOM B", "Selesai", R.drawable.fotoparkir))
        reservasiAdapter.notifyDataSetChanged()
    }

    private fun navigateToDetail(reservasi: Reservasi) {
        val intent = Intent(this, DetailListParkirActivity::class.java)
        intent.putExtra("EXTRA_RESERVASI", reservasi)
        startActivity(intent)
    }



}
