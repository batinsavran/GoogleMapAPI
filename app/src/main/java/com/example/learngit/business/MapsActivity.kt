package com.example.learngit.business

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.learngit.R
import com.example.learngit.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import java.util.Locale

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var currentLocation: Location
    private lateinit var centerMarker: ImageView
    private lateinit var searchBar: EditText
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        centerMarker = findViewById(R.id.center_marker)
        searchBar = findViewById(R.id.search_bar)

        val confirmButton: Button = findViewById(R.id.confirmButton)
        confirmButton.setOnClickListener {
            val markerPosition = mMap.cameraPosition.target
            val address = getAddressFromLatLng(markerPosition)
            val intent = Intent().apply {
                putExtra("address", address.getAddressLine(0))
                putExtra("locality", address.locality)
                putExtra("state", address.adminArea)
                putExtra("country", address.countryName)
                putExtra("postalCode", address.postalCode)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        currentLocation = location
                        val currentLatLng = LatLng(location.latitude, location.longitude)
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                    }
                }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }

        mMap.setOnCameraIdleListener {
            val centerPosition = mMap.cameraPosition.target
            updateSearchBarWithAddress(centerPosition)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                mMap.isMyLocationEnabled = true
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        if (location != null) {
                            currentLocation = location
                            val currentLatLng = LatLng(location.latitude, location.longitude)
                            mMap.animateCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    currentLatLng,
                                    15f
                                )
                            )
                        }
                    }
            }
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateSearchBarWithAddress(latLng: LatLng) {
        val address = getAddressFromLatLng(latLng)
        searchBar.setText(address.getAddressLine(0))
    }

    private fun getAddressFromLatLng(latLng: LatLng): Address {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address>? = try {
            geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        return if (!addresses.isNullOrEmpty()) {
            addresses[0]
        } else {
            Address(Locale.getDefault()).apply {
                setAddressLine(0, "Address not found")
            }
        }
    }

}
