package com.example.parkirkuy.ui

import LocationAdapter
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import org.osmdroid.views.overlay.OverlayItem
import java.io.IOException
import java.nio.charset.StandardCharsets

class MapsActivity : AppCompatActivity() {
    private lateinit var mapController: MapController
    private lateinit var adapter: LocationAdapter
    private var modelMainList: MutableList<ModelMain> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))

        initMap()
        setupRecyclerView()
//        setupSearchView()
        loadLocationMarkers() // Load and show markers
    }

    private fun initMap() {
        val mapView = findViewById<org.osmdroid.views.MapView>(R.id.mapView)
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        mapView.setMultiTouchControls(true)
        mapView.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)

        mapController = mapView.controller as MapController
        val jakartaGeoPoint = GeoPoint(-6.2088, 106.8456)
        mapController.setCenter(jakartaGeoPoint)
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

                // Assign related object to the marker to pass it to the custom info window
                relatedObject = model

                // Use the custom InfoWindow
                setInfoWindow(CustomInfoWindow(mapView))
            }

            mapView.overlays.add(marker)
        }
        mapView.invalidate()
    }


    private fun showLocationInfo(model: ModelMain) {
        val inflater = LayoutInflater.from(this)
        val tooltipView = inflater.inflate(R.layout.layout_tooltip, null)

        val tvNamaLokasiTooltip = tooltipView.findViewById<TextView>(R.id.tvNamaLokasi)
        val tvAlamatTooltip = tooltipView.findViewById<TextView>(R.id.tvAlamat)
        val imageCloseTooltip = tooltipView.findViewById<ImageView>(R.id.imageClose)
        val cardViewTooltip = tooltipView.findViewById<CardView>(R.id.cvContent)

        tvNamaLokasiTooltip.text = model.strName
        tvAlamatTooltip.text = model.strVicinity
        cardViewTooltip.visibility = View.VISIBLE

        imageCloseTooltip.setOnClickListener {
            cardViewTooltip.visibility = View.GONE
        }

        val layoutContainer: ViewGroup = findViewById(R.id.rootLayout)
        layoutContainer.addView(tooltipView)
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
        adapter = LocationAdapter(modelMainList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupSearchView() {
        val searchView = findViewById<androidx.appcompat.widget.SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { filterLocations(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { filterLocations(it) }
                return true
            }
        })
    }

    private fun filterLocations(query: String) {
        val filteredList = modelMainList.filter {
            it.strName.contains(query, ignoreCase = true) ||
                    it.strVicinity.contains(query, ignoreCase = true)
        }
        adapter.updateData(filteredList)
    }

    override fun onResume() {
        super.onResume()
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        findViewById<org.osmdroid.views.MapView>(R.id.mapView).onResume()
    }

    override fun onPause() {
        super.onPause()
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        findViewById<org.osmdroid.views.MapView>(R.id.mapView).onPause()
    }
}
