import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.parkirkuy.R
import com.example.parkirkuy.ui.ModelMain

class LocationAdapter(
    private var locations: List<ModelMain>,
    private val onLocationClick: (ModelMain) -> Unit // Callback untuk menangani klik lokasi
) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    // Fungsi untuk memperbarui data di adapter
    fun updateData(newData: List<ModelMain>) {
        locations = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_location, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locations[position]
        holder.bind(location, onLocationClick)
    }

    override fun getItemCount(): Int = locations.size

    class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val locationButton: Button = itemView.findViewById(R.id.locationButton)

        // Fungsi untuk mengikat data ke tampilan
        fun bind(location: ModelMain, onClick: (ModelMain) -> Unit) {
            // Gabungkan nama lokasi dan alamat lokasi ke dalam satu tombol
            val locationText = "${location.strName}\n${location.strVicinity}"
            locationButton.text = locationText

            // Pasang listener klik untuk tombol
            locationButton.setOnClickListener {
                onClick(location)
            }
        }
    }
}
