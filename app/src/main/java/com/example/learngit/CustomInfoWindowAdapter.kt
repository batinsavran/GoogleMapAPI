import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.learngit.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfoWindowAdapter(private val inflater: LayoutInflater) : GoogleMap.InfoWindowAdapter {

    private var currentMarker: Marker? = null

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }

    override fun getInfoContents(marker: Marker): View {
        val view = inflater.inflate(R.layout.item_business, null)

        val businessNameTextView = view.findViewById<TextView>(R.id.businessNameTextView)
        val kitchenTypeTextView = view.findViewById<TextView>(R.id.kitchenTypeTextView)
        val locationTextView = view.findViewById<TextView>(R.id.locationTextView)
        val businessImageView = view.findViewById<ImageView>(R.id.businessPhotoImageView)

        businessNameTextView.text = marker.title ?: "N/A"
        kitchenTypeTextView.text = marker.snippet ?: "N/A"
        locationTextView.text = "5 km"
        businessImageView.setImageResource(R.drawable.card_img)

        if (currentMarker != marker) {
            currentMarker?.hideInfoWindow()
            currentMarker = marker
        }

        return view
    }

    fun showMarkerInfoWindow(marker: Marker) {
        currentMarker = marker
        marker.showInfoWindow()
    }

    fun hideMarkerInfoWindow() {
        currentMarker?.hideInfoWindow()
        currentMarker = null
    }
}
