//package com.example.parkirkuy.ui
//
//import com.example.parkirkuy.R
//import android.graphics.Color
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.jjoe64.graphview.GraphView
//import com.jjoe64.graphview.GridLabelRenderer
//import com.jjoe64.graphview.series.BarGraphSeries
//import com.jjoe64.graphview.series.DataPoint
//
//
//class DetailParkir : AppCompatActivity() {
//
//    private var graphView: GraphView? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.detail_parkir)
//
//        // Inisialisasi GraphView
//        graphView = findViewById(R.id.graphView)
//
//        // Data pie chart (misal kapasitas parkir)
//        val availableParking = 60.0 // 60% available
//        val occupiedParking = 40.0 // 40% occupied
//
//        // Membuat data untuk pie chart (dengan DataPoint)
//        val dataPoints = arrayOf(
//            DataPoint(0.0, availableParking),
//            DataPoint(1.0, occupiedParking)
//        )
//
//        // Membuat BarGraphSeries untuk menggambarkan data
//        val series = BarGraphSeries(dataPoints)
//        series.color = Color.GREEN  // Set warna untuk bar pertama (available)
//        series.spacing = 10         // Set jarak antar bar (untuk tampilan lebih baik)
//
//        // Menambahkan series ke GraphView
//        graphView?.addSeries(series)
//
//        // Memodifikasi GridLabelRenderer untuk mematikan grid (lebih mirip dengan pie chart)
//        graphView?.gridLabelRenderer?.gridStyle = GridLabelRenderer.GridStyle.NONE
//
//        // Menyembunyikan X dan Y Axis karena kita hanya ingin menampilkan data
//        graphView?.gridLabelRenderer?.isHorizontalLabelsVisible = false
//        graphView?.gridLabelRenderer?.isVerticalLabelsVisible = false
//
//        // Modifikasi Viewport untuk membuat tampilan lebih menyerupai Pie Chart
//        graphView?.viewport?.apply {
//            // Set viewport scaling untuk hanya dapat di-scroll horizontal, tidak vertikal
//            isScalable = true
//            setScalableY(false)   // Matikan scroll vertikal
//            setScalableX(true)    // Biarkan scroll horizontal
//
//            // Matikan scroll vertikal
//            isScrollable = true
//            setScrollableY(false)
//        }
//    }
//}