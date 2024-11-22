package com.example.parkirkuy.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parkirkuy.R
import com.example.parkirkuy.ui.Reservasi

class ReservasiAdapter(
    private val reservasiList: List<Reservasi>,
    private val onReservasiClicked: (Reservasi) -> Unit
) : RecyclerView.Adapter<ReservasiAdapter.ReservasiViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservasiViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_listdetailreservasi, parent, false)
        return ReservasiViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservasiViewHolder, position: Int) {
        val reservasi = reservasiList[position]
        holder.bind(reservasi, onReservasiClicked)
    }

    override fun getItemCount(): Int = reservasiList.size

    inner class ReservasiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tanggalTextView: TextView = itemView.findViewById(R.id.tanggalreservasi)
        private val lokasiTextView: TextView = itemView.findViewById(R.id.lokasiparkir)
        private val statusTextView: TextView = itemView.findViewById(R.id.statusreservasi)
        private val fotoImageView: ImageView = itemView.findViewById(R.id.fotolokasiparkir)
        private val detailButton: Button = itemView.findViewById(R.id.button_reservasi)

        fun bind(reservasi: Reservasi, onReservasiClicked: (Reservasi) -> Unit) {
            tanggalTextView.text = reservasi.tanggal
            lokasiTextView.text = reservasi.lokasi
            statusTextView.text = reservasi.status
            fotoImageView.setImageResource(reservasi.foto)

            detailButton.setOnClickListener {
                onReservasiClicked(reservasi)
            }
        }
    }
}
