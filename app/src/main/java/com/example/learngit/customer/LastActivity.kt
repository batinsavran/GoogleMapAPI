package com.example.learngit.customer

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.learngit.R
import com.example.learngit.databinding.ActivityLastBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.maps.android.SphericalUtil
import java.util.Locale

class LastActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: ActivityLastBinding
    private var currentLatLng: LatLng? = null

    private lateinit var defaultIcon: BitmapDescriptor
    private lateinit var clickedIcon: BitmapDescriptor
    private var lastClickedMarker: Marker? = null

    private val category1Businesses = listOf(
        Business(
            "Restorant A",
            LatLng(36.7391, 29.9270),
            R.drawable.restaurant_a_image,
            "Türk Mutfağı"
        ),
        Business(
            "Restorant B",
            LatLng(36.7320, 29.9145),
            R.drawable.restaurant_b_image,
            "İtalyan Mutfağı"
        )
    )

    private val category2Businesses = listOf(
        Business("Cafe C", LatLng(36.7360, 29.9300), R.drawable.cafe_c_image, "Kahve"),
        Business("Cafe D", LatLng(36.7380, 29.9240), R.drawable.cafe_d_image, "Tatlı")
    )

    private val category3Businesses = listOf(
        Business("Meyhane E", LatLng(36.7330, 29.9160), R.drawable.meyhane_e_image, "Meyhane"),
        Business("Meyhane F", LatLng(36.7340, 29.9180), R.drawable.meyhane_f_image, "Meyhane")
    )

    private val category4Businesses = listOf(
        Business("Bar G", LatLng(37.4118, -122.0897), R.drawable.bar_g_image, "Bar"),
        Business("Bar H", LatLng(37.4087, -122.0952), R.drawable.bar_h_image, "Bar")
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()
        initializeLocationClient()
        initializeMapFragment()
        setupChips()

        binding.filter.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_discover -> true
                R.id.nav_reservations -> true
                R.id.nav_messages -> true
                R.id.nav_profile -> true
                else -> false
            }
        }
    }

    private fun initializeLocationClient() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun initializeMapFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setupChips() {
        binding.chip1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) showBusinesses(category1Businesses)
        }

        binding.chip2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) showBusinesses(category2Businesses)
        }

        binding.chip3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) showBusinesses(category3Businesses)
        }

        binding.chip4.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) showBusinesses(category4Businesses)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        defaultIcon = BitmapDescriptorFactory.fromResource(R.drawable.location)
        clickedIcon = BitmapDescriptorFactory.fromResource(R.drawable.selected_marker)

        setupMapUI()
        checkLocationPermission()

        mMap.setOnCameraIdleListener {
            updateMarkers()
        }

        mMap.setOnMarkerClickListener { marker ->
            handleMarkerClick(marker)
            true
        }

        mMap.setOnMapClickListener {
            handleMapClick()
        }
    }

    private fun setupMapUI() {
        mMap.uiSettings.isMyLocationButtonEnabled = false
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
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
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    currentLatLng = LatLng(location.latitude, location.longitude)
                    addCurrentLocationMarker()
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng!!, 15f))
                    showBusinesses(category1Businesses + category2Businesses + category3Businesses)
                }
            }
    }

    private fun addCurrentLocationMarker() {
        currentLatLng?.let {
            mMap.addMarker(
                MarkerOptions()
                    .position(it)
                    .title("Mevcut Konum")
            )
        }
    }

    private fun handleMarkerClick(marker: Marker) {
        lastClickedMarker?.let {
            if (it != marker) {
                it.setIcon(defaultIcon)
            }
        }
        marker.setIcon(clickedIcon)
        lastClickedMarker = marker
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 15f))
        showBusinessDetails(marker)
    }

    private fun handleMapClick() {
        lastClickedMarker?.let {
            it.setIcon(defaultIcon)
            lastClickedMarker = null
            binding.cardView.visibility = View.GONE
        }
    }

    private fun updateMarkers() {
        val centerLatLng = mMap.cameraPosition.target
        val radiusInMeters = 5000.0

        val allBusinesses =
            category1Businesses + category2Businesses + category3Businesses + category4Businesses

        mMap.clear()

        for (business in allBusinesses) {
            val businessLatLng = business.location
            if (getDistanceInMeters(centerLatLng, businessLatLng) <= radiusInMeters) {
                addBusinessMarker(business)
            }
        }
    }

    private fun showBusinesses(businesses: List<Business>) {
        val centerLatLng = mMap.cameraPosition.target
        val radiusInMeters = 5000.0

        mMap.clear()
        currentLatLng?.let {
            for (business in businesses) {
                if (getDistanceInMeters(centerLatLng, business.location) <= radiusInMeters) {
                    addBusinessMarker(business)
                }
            }
        }
    }

    private fun addBusinessMarker(business: Business) {
        val drawable = ContextCompat.getDrawable(this, R.drawable.location)
        val bitmap = Bitmap.createBitmap(
            drawable!!.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        val icon = BitmapDescriptorFactory.fromBitmap(bitmap)

        mMap.addMarker(
            MarkerOptions()
                .position(business.location)
                .title(business.name)
                .icon(icon)
                .snippet("Bar")
        )
    }

    private fun showBusinessDetails(marker: Marker) {
        binding.cardView.visibility = View.VISIBLE

        val business =
            (category1Businesses + category2Businesses + category3Businesses + category4Businesses)
                .find { it.name == marker.title }

        business?.let {
            binding.businessNameTextView.text = it.name
            binding.kitchenTypeTextView.text = it.kitchenType
            binding.businessPhotoImageView.setImageResource(it.imageResId)
            binding.locationTextView.text = getDistanceText(marker.position)
        }
    }


    private fun getDistanceText(businessLatLng: LatLng): String {
        val distance = getDistanceInMeters(currentLatLng!!, businessLatLng)
        return String.format(Locale.getDefault(), "%.2f km", distance / 1000)
    }

    private fun getDistanceInMeters(start: LatLng, end: LatLng): Double {
        return SphericalUtil.computeDistanceBetween(start, end)
    }

    private fun showBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.bottomsheet_filter, null)
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }
}


data class Business(
    val name: String,
    val location: LatLng,
    val imageResId: Int,
    val kitchenType: String
)

