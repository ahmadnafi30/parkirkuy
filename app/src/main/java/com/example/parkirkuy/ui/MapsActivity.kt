package com.example.parkirkuy.ui

import LocationAdapter
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parkirkuy.R
import org.json.JSONException
import org.json.JSONObject
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapController
import org.osmdroid.views.overlay.Marker
import java.io.IOException
import java.nio.charset.StandardCharsets

class MapsActivity : AppCompatActivity() {
    private lateinit var mapController: MapController
    private lateinit var adapter: LocationAdapter
    private lateinit var mapView: org.osmdroid.views.MapView
    private var modelMainList: MutableList<ModelMain> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        Configuration.getInstance().load(applicationContext, PreferenceManager.getDefaultSharedPreferences(applicationContext))
        initMap()
        setupRecyclerView()
        loadLocationMarkers()
        // Inisialisasi tombol zoom
//        val zoomInButton = findViewById<ImageButton>(R.id.zoomin)
//        val zoomOutButton = findViewById<ImageButton>(R.id.zoomout)
//
//// Event untuk zoom in
//        zoomInButton.setOnClickListener {
//            val currentZoomLevel = mapView.zoomLevelDouble
//            if (currentZoomLevel < mapView.maxZoomLevel) {
//                mapController.zoomIn()
//                mapView.invalidate()
//            } else {
//                Toast.makeText(this, "Zoom in maksimum tercapai", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//// Event untuk zoom out
//        zoomOutButton.setOnClickListener {
//            val currentZoomLevel = mapView.zoomLevelDouble
//            if (currentZoomLevel > mapView.minZoomLevel) {
//                mapController.zoomOut()
//                mapView.invalidate()
//            } else {
//                Toast.makeText(this, "Zoom out minimum tercapai", Toast.LENGTH_SHORT).show()
//            }
//        }

        setupZoomButtons()

        setupNotificationButton()


    }

    private fun setupNotificationButton() {
        val notificationButton = findViewById<ImageButton>(R.id.gotonotifikasi)
        notificationButton.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }
    }


    private fun setupZoomButtons() {
        val zoomInButton = findViewById<ImageButton>(R.id.zoomin)
        val zoomOutButton = findViewById<ImageButton>(R.id.zoomout)

        zoomInButton.setOnClickListener {
            val currentZoomLevel = mapView.zoomLevelDouble
            mapView.controller.setZoom(currentZoomLevel + 1)
        }

        zoomOutButton.setOnClickListener {
            val currentZoomLevel = mapView.zoomLevelDouble
            mapView.controller.setZoom(currentZoomLevel - 1)
        }
    }


    private fun initMap() {
        val mapView = findViewById<org.osmdroid.views.MapView>(R.id.mapView)
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        mapView.setMultiTouchControls(true)
        mapView.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)

        mapController = mapView.controller as MapController
        val initialGeoPoint = GeoPoint( -7.95235, 112.61296) // Jakarta
        mapController.setCenter(initialGeoPoint)
        mapController.zoomTo(15)
    }

    private fun loadLocationMarkers() {
        try {
            val stream = assets.open("sample_maps.json")
            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val jsonString = String(buffer, StandardCharsets.UTF_8)

            val jsonObject = JSONObject(jsonString)
            val jsonArrayResults = jsonObject.getJSONArray("results")
            for (i in 0 until jsonArrayResults.length()) {
                val jsonObjectResult = jsonArrayResults.getJSONObject(i)
                val modelMain = ModelMain().apply {
                    strName = jsonObjectResult.optString("name", "Unknown Name")
                    strVicinity = jsonObjectResult.optString("vicinity", "Unknown Vicinity")

                    val jsonGeometry = jsonObjectResult.optJSONObject("geometry")
                    val jsonLocation = jsonGeometry?.optJSONObject("location")
                    latLoc = jsonLocation?.optDouble("lat", 0.0) ?: 0.0
                    longLoc = jsonLocation?.optDouble("lng", 0.0) ?: 0.0

                    val parkingCapacity = jsonObjectResult.optJSONObject("parking_capacity")
                    totalCapacity = parkingCapacity?.optInt("total", 0) ?: 0
                    availableCapacity = parkingCapacity?.optInt("available", 0) ?: 0
                }
                modelMainList.add(modelMain)
            }
            initMarkers(modelMainList)
            adapter.updateData(modelMainList)
        } catch (e: IOException) {
            Toast.makeText(this, "Failed to load location data.", Toast.LENGTH_SHORT).show()
        } catch (e: JSONException) {
            Toast.makeText(this, "Invalid JSON format.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initMarkers(modelList: List<ModelMain>) {
        val mapView = findViewById<org.osmdroid.views.MapView>(R.id.mapView)

        for (model in modelList) {
            val geoPoint = GeoPoint(model.latLoc, model.longLoc)

            val originalIcon = resources.getDrawable(R.drawable.gps, null) as BitmapDrawable
            val scaledIcon = Bitmap.createScaledBitmap(
                originalIcon.bitmap,
                originalIcon.intrinsicWidth / 50,
                originalIcon.intrinsicHeight / 50,
                false
            )
            val markerIcon = BitmapDrawable(resources, scaledIcon)

            val marker = Marker(mapView).apply {
                position = geoPoint
                icon = markerIcon
                title = model.strName
                snippet = model.strVicinity
                relatedObject = model // Tautkan data ModelMain ke marker
            }

            val customInfoWindow = CustomInfoWindow(mapView, this)
            marker.infoWindow = customInfoWindow

            // Tambahkan listener untuk klik marker
            marker.setOnMarkerClickListener { clickedMarker, map ->
                clickedMarker.showInfoWindow() // Tampilkan InfoWindow
                mapController.animateTo(clickedMarker.position) // Fokus ke marker
                true
            }

            mapView.overlays.add(marker)
        }
        mapView.invalidate()
    }


    private fun setupRecyclerView() {
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
        adapter = LocationAdapter(modelMainList) { model ->
            showTooltip(model)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun showTooltip(model: ModelMain) {
        val inflater = LayoutInflater.from(this)
        val tooltipView = inflater.inflate(R.layout.layout_tooltip, null)

        val tvNamaLokasiTooltip = tooltipView.findViewById<TextView>(R.id.tvNamaLokasi)
        val tvAlamatTooltip = tooltipView.findViewById<TextView>(R.id.tvAlamat)
        val btnDetail = tooltipView.findViewById<Button>(R.id.btnDetail)
        val imageCloseTooltip = tooltipView.findViewById<ImageView>(R.id.imageClose)
        val cardViewTooltip = tooltipView.findViewById<CardView>(R.id.cvContent)

        tvNamaLokasiTooltip.text = model.strName
        tvAlamatTooltip.text = model.strVicinity
        cardViewTooltip.visibility = View.VISIBLE

        val geoPoint = GeoPoint(model.latLoc, model.longLoc)
        mapController.animateTo(geoPoint)

        // Tampilkan info window pada marker yang sesuai
        val mapView = findViewById<org.osmdroid.views.MapView>(R.id.mapView)
        for (overlay in mapView.overlays) {
            if (overlay is Marker && overlay.position.latitude == model.latLoc && overlay.position.longitude == model.longLoc) {
                overlay.showInfoWindow()
            } else if (overlay is Marker) {
                overlay.closeInfoWindow()
            }
        }
        mapView.invalidate()

//        btnDetail.setOnClickListener {
//            Toast.makeText(this, "Lihat detail lokasi: ${model.strName}", Toast.LENGTH_SHORT).show()
//        }
//
//        imageCloseTooltip.setOnClickListener {
//            cardViewTooltip.visibility = View.GONE
//        }

//        val layoutContainer: ViewGroup = findViewById(R.id.rootLayout)
//        layoutContainer.addView(tooltipView)
    }
}
