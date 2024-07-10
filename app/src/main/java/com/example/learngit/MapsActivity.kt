package com.example.learngit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.learngit.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

//        val businesses = listOf(
//            LatLng(41.0082, 28.9784), // A
//            LatLng(39.9334, 32.8597), // B
//            LatLng(38.4237, 27.1428), // C
//            LatLng(36.8841, 30.7056)  // D
//        )
//
//
//        businesses.forEach { location ->
//            mMap.addMarker(MarkerOptions().position(location).title("com.example.learngit.Business at ${location.latitude}, ${location.longitude}"))
//        }
//
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(businesses[0], 12f))

        val istanbul = LatLng(41.0082, 28.9784)
        mMap.addMarker(MarkerOptions().position(istanbul).title("Marker in Istanbul"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(istanbul, 12f))
    }

}