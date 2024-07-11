//package com.example.learngit
//
//import com.example.learngit.customer.CustomInfoWindowAdapter
//import android.Manifest
//import android.content.pm.PackageManager
//import android.graphics.Bitmap
//import android.graphics.Canvas
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import com.example.learngit.databinding.ActivityMain2Binding
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationServices
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.BitmapDescriptorFactory
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.Marker
//import com.google.android.gms.maps.model.MarkerOptions
//import com.google.android.material.bottomnavigation.BottomNavigationView
//import com.google.maps.android.SphericalUtil
//
//class MainActivity2 : AppCompatActivity(), OnMapReadyCallback {
//
//    private lateinit var mMap: GoogleMap
//    private lateinit var fusedLocationClient: FusedLocationProviderClient
//    private lateinit var binding: ActivityMain2Binding
//    private var currentLatLng: LatLng? = null
//    private lateinit var customInfoWindowAdapter: com.example.learngit.customer.CustomInfoWindowAdapter
//
////    private val category1Businesses = listOf(
////        Business("Restorant A", LatLng(36.7391, 29.9270)),
////        Business("Restorant B", LatLng(36.7320, 29.9145))
////    )
////
////    private val category2Businesses = listOf(
////        Business("Cafe C", LatLng(36.7360, 29.9300)),
////        Business("Cafe D", LatLng(36.7380, 29.9240))
////    )
////
////    private val category3Businesses = listOf(
////        Business("Meyhane E", LatLng(36.7330, 29.9160)),
////        Business("Meyhane F", LatLng(36.7340, 29.9180))
////    )
////
////    private val category4Businesses = listOf(
////        Business("Bar G", LatLng(37.4118, -122.0897)),
////        Business("Bar H", LatLng(37.4087, -122.0952))
////    )
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMain2Binding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
//        bottomNavigationView.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.nav_home -> true
//                R.id.nav_discover -> true
//                R.id.nav_reservations -> true
//                R.id.nav_messages -> true
//                R.id.nav_profile -> true
//                else -> false
//            }
//        }
//
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//
//        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//
//        binding.chip1.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) showBusinesses(category1Businesses)
//        }
//
//        binding.chip2.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) showBusinesses(category2Businesses)
//        }
//
//        binding.chip3.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) showBusinesses(category3Businesses)
//        }
//
//        binding.chip4.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) showBusinesses(category4Businesses)
//        }
//    }
//
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//
//        mMap.uiSettings.isMyLocationButtonEnabled = false
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//            == PackageManager.PERMISSION_GRANTED
//        ) {
//            mMap.isMyLocationEnabled = true
//            getCurrentLocation()
//        } else {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
//            )
//        }
//
//        mMap.setOnCameraIdleListener {
//            updateMarkers()
//        }
//
//        customInfoWindowAdapter = com.example.learngit.customer.CustomInfoWindowAdapter(layoutInflater)
//        mMap.setInfoWindowAdapter(customInfoWindowAdapter)
//
//        mMap.setOnMarkerClickListener { marker ->
//            customInfoWindowAdapter.showMarkerInfoWindow(marker)
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 15f))
//            true
//        }
//    }
//
//
//    private fun getCurrentLocation() {
//        fusedLocationClient.lastLocation
//            .addOnSuccessListener { location ->
//                if (location != null) {
//                    currentLatLng = LatLng(location.latitude, location.longitude)
//                    mMap.addMarker(
//                        MarkerOptions()
//                            .position(currentLatLng!!)
//                            .title("Mevcut Konum")
//                    )
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng!!, 15f))
//
//                    showBusinesses(category1Businesses + category2Businesses + category3Businesses)
//                }
//            }
//    }
//
//    private fun updateMarkers() {
//        val centerLatLng = mMap.cameraPosition.target
//        val radiusInMeters = 5000.0
//
//        val currentMarkers = mutableListOf<Marker>()
//
//        val allBusinesses =
//            category1Businesses + category2Businesses + category3Businesses + category4Businesses
//
//        for (business in allBusinesses) {
//            val businessLatLng = business.location
//            val distance = getDistanceInMeters(centerLatLng, businessLatLng)
//            if (distance <= radiusInMeters) {
//                val drawable = ContextCompat.getDrawable(this, R.drawable.location)
//                val bitmap = Bitmap.createBitmap(
//                    drawable!!.intrinsicWidth,
//                    drawable.intrinsicHeight,
//                    Bitmap.Config.ARGB_8888
//                )
//                val canvas = Canvas(bitmap)
//                drawable.setBounds(0, 0, canvas.width, canvas.height)
//                drawable.draw(canvas)
//                val icon = BitmapDescriptorFactory.fromBitmap(bitmap)
//
//                val marker = mMap.addMarker(
//                    MarkerOptions()
//                        .position(businessLatLng)
//                        .title(business.name)
//                        .icon(icon)
//                        .snippet("Restoran")
//                )
//
//                if (marker != null) {
//                    currentMarkers.add(marker)
//                }
//            }
//        }
//
//        mMap.setOnMarkerClickListener { marker ->
//            customInfoWindowAdapter.showMarkerInfoWindow(marker)
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 15f))
//            true
//        }
//    }
//
//
//    private fun showBusinesses(businesses: List<Business>) {
//        val centerLatLng = mMap.cameraPosition.target
//        val radiusInMeters = 5000.0
//
//        mMap.clear()
//
//        currentLatLng?.let {
//            for (business in businesses) {
//                val businessLatLng = business.location
//                val distance = getDistanceInMeters(centerLatLng, businessLatLng)
//                if (distance <= radiusInMeters) {
//                    val drawable = ContextCompat.getDrawable(this, R.drawable.location)
//                    val bitmap = Bitmap.createBitmap(
//                        drawable!!.intrinsicWidth,
//                        drawable.intrinsicHeight,
//                        Bitmap.Config.ARGB_8888
//                    )
//                    val canvas = Canvas(bitmap)
//                    drawable.setBounds(0, 0, canvas.width, canvas.height)
//                    drawable.draw(canvas)
//                    val icon = BitmapDescriptorFactory.fromBitmap(bitmap)
//
//                    mMap.addMarker(
//                        MarkerOptions()
//                            .position(businessLatLng)
//                            .title(business.name)
//                            .icon(icon)
//                            .snippet("Restoran")
//                    )
//                }
//            }
//        }
//    }
//
//
//    private fun getDistanceInMeters(start: LatLng, end: LatLng): Double {
//        return SphericalUtil.computeDistanceBetween(start, end)
//    }
//}
//
////data class com.example.learngit.Business(val name: String, val location: LatLng)
