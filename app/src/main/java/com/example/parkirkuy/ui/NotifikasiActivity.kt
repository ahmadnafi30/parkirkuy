package com.example.parkirkuy.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parkirkuy.R

class NotificationActivity : AppCompatActivity() {

    private lateinit var notificationRecyclerView: RecyclerView
    private lateinit var notificationAdapter: NotificationAdapter
    private val notificationList = mutableListOf<NotificationModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifikasi)

        // Tombol kembali
        val backButton = findViewById<ImageButton>(R.id.backbutton)
        backButton.setOnClickListener {
            onBackPressed() // Navigasi ke halaman sebelumnya
        }

        // RecyclerView setup
        notificationRecyclerView = findViewById(R.id.recyclerViewNotifications)
        notificationAdapter = NotificationAdapter(notificationList) { notification ->
            Toast.makeText(this, "Clicked: ${notification.message}", Toast.LENGTH_SHORT).show()
        }

        notificationRecyclerView.layoutManager = LinearLayoutManager(this)
        notificationRecyclerView.adapter = notificationAdapter

        loadDummyNotifications()
    }

    private fun loadDummyNotifications() {
        // Data dummy untuk notifikasi
        val dummyNotifications = listOf(
            NotificationModel("10 November, 08:00", "Reservasi parkir Anda untuk lokasi 'Parkir A' berhasil dilakukan."),
            NotificationModel("11 November, 12:00", "Waktu reservasi Anda hampir habis."),
            NotificationModel("12 November, 15:30", "Pembayaran denda Anda telah berhasil.")
        )
        notificationList.addAll(dummyNotifications)
        notificationAdapter.notifyDataSetChanged()
    }
}
