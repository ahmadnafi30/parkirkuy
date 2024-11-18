package com.example.parkirkuy.ui

import com.example.parkirkuy.R
import com.example.parkirkuy.databinding.LayoutTooltipBinding
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow

class CustomInfoWindow(mapView: MapView?) : InfoWindow(R.layout.layout_tooltip, mapView) {

    private lateinit var binding: LayoutTooltipBinding

    override fun onClose() {
        // No action when closing
    }

    override fun onOpen(item: Any) {
        val marker = item as Marker
        val infoWindowData = marker.relatedObject as ModelMain

        // Use View Binding to reference the views
        binding = LayoutTooltipBinding.bind(mView)  // mView is the root view of the info window

        // Set the data from the ModelMain object
        binding.tvNamaLokasi.text = infoWindowData.strName
        binding.tvAlamat.text = infoWindowData.strVicinity

        // Handle the close button click
        binding.imageClose.setOnClickListener {
            marker.closeInfoWindow()
        }
    }
}
