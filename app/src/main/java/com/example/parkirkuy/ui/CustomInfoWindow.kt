import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.parkirkuy.R
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

        tvNamaLokasi.text = model.strName
        tvAlamat.text = model.strVicinity

        btnDetail.setOnClickListener {
            Toast.makeText(context, "Detail lokasi: ${model.strName}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClose() {
        // Kosongkan jika tidak ada aksi tambahan
    }
}
