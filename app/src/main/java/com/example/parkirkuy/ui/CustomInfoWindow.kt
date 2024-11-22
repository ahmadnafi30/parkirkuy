package com.example.parkirkuy.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.parkirkuy.R
import com.example.parkirkuy.ui.DetailActivity
import com.example.parkirkuy.ui.ModelMain
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow

class CustomInfoWindow(mapView: MapView, private val context: Context) : InfoWindow(R.layout.layout_tooltip, mapView) {

    override fun onOpen(item: Any?) {
        val model = (item as? Marker)?.relatedObject as? ModelMain ?: return

        val tvNamaLokasi = mView.findViewById<TextView>(R.id.tvNamaLokasi)
        val tvAlamat = mView.findViewById<TextView>(R.id.tvAlamat)
        val btnDetail = mView.findViewById<Button>(R.id.btnDetail)

        // Set data ke elemen view
        tvNamaLokasi.text = model.strName
        tvAlamat.text = model.strVicinity

        // Set klik tombol untuk membuka DetailActivity
        btnDetail.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("NAMA_PARKIR", model.strName)
            intent.putExtra("KAPASITAS_PARKIR", "${model.availableCapacity}/${model.totalCapacity}")
            intent.putExtra("LOKASI_PARKIR", model.strVicinity)
            intent.putExtra("LAT", model.latLoc)
            intent.putExtra("LNG", model.longLoc)
            context.startActivity(intent)
        }

    }

    override fun onClose() {

    }
}
