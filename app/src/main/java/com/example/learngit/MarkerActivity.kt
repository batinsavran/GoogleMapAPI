package com.example.learngit

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.maps.android.SphericalUtil

class MarkerActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currentLatLng: LatLng? = null // Sınıf düzeyinde currentLatLng

    private val category1Businesses = listOf(
        Business("Business A", LatLng(37.3960, -122.0920)),
        Business("Business B", LatLng(37.3795, -122.0750))
    )

    private val category2Businesses = listOf(
        Business("Business C", LatLng(37.3890, -122.0860)),
        Business("Business D", LatLng(37.4000, -122.0700))
    )

    private val category3Businesses = listOf(
        Business("Business E", LatLng(37.3850, -122.1000)),
        Business("Business F", LatLng(37.3800, -122.0800))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marker)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val chipGroup = findViewById<ChipGroup>(R.id.chipGroup)
        val chip1 = findViewById<Chip>(R.id.chip1)
        val chip2 = findViewById<Chip>(R.id.chip2)
        val chip3 = findViewById<Chip>(R.id.chip3)

        chip1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) showBusinesses(category1Businesses)
        }

        chip2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) showBusinesses(category2Businesses)
        }

        chip3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) showBusinesses(category3Businesses)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            getCurrentLocation()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
            )
        }
    }

    private fun getCurrentLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    currentLatLng = LatLng(location.latitude, location.longitude)
                    mMap.addMarker(
                        MarkerOptions()
                            .position(currentLatLng!!)
                            .title("Mevcut Konum")
                    )
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng!!, 15f))

                    showBusinesses(category1Businesses + category2Businesses + category3Businesses)
                }
            }
    }

    private fun showBusinesses(businesses: List<Business>) {
        mMap.clear() // Mevcut işaretleyicileri temizle
        val radiusInMeters = 5000.0 // 5 km

        currentLatLng?.let { currentLocation ->
            for (business in businesses) {
                val businessLatLng = business.location
                val distance = getDistanceInMeters(currentLocation, businessLatLng)
                if (distance <= radiusInMeters) {
                    val icon =
                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                    mMap.addMarker(
                        MarkerOptions()
                            .position(businessLatLng)
                            .title(business.name)
                            .icon(icon)
                    )
                }
            }
        }
    }

    private fun getDistanceInMeters(start: LatLng, end: LatLng): Double {
        return SphericalUtil.computeDistanceBetween(start, end)
    }
}

//data class Business(val name: String, val location: LatLng)
