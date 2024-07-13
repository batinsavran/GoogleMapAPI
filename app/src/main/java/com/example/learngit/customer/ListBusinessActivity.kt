package com.example.learngit.customer

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learngit.databinding.ActivityListBusinessBinding
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Locale

class ListBusinessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBusinessBinding
    private lateinit var businessAdapter: BusinessAdapter
    private val businessList: MutableList<Pair<Business, String>> = mutableListOf()
    private val allBusinesses: MutableList<Pair<Business, String>> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBusinessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadBusinessesFromIntent()

        binding.mapButton.setOnClickListener {
            val intent = Intent(this, LastActivity::class.java)
            startActivity(intent)
        }

//        binding.chipGroup.setOnCheckedChangeListener { group, _ ->
//            val selectedChipIds = group.checkedChipIds
//
//            val selectedCriteria = selectedChipIds.mapNotNull { chipId ->
//                when (chipId) {
//                    R.id.chip_list_restoran -> "Restoran"
//                    R.id.chip_list_Cafe -> "Cafe"
//                    R.id.chip_list_Meyhane -> "Meyhane"
//                    R.id.chip_list_Bar -> "Bar"
//                    R.id.chip_list_Kahve -> "Kahve"
//                    R.id.chip_list_Pastane -> "Pastane"
//                    else -> null
//                }
//            }
//
//            val filterCriteria = selectedCriteria.joinToString(", ")
//            filterBusinesses(filterCriteria)
//        }
    }

    private fun setupRecyclerView() {
        businessAdapter = BusinessAdapter(businessList)
        binding.listBusiness.apply {
            layoutManager = LinearLayoutManager(this@ListBusinessActivity)
            adapter = businessAdapter
        }
    }

    private fun loadBusinessesFromIntent() {
        val businessListJson = intent.getStringExtra("business_list")
        val gson = Gson()
        val businessType = object : TypeToken<List<Business>>() {}.type
        val businesses: List<Business> = gson.fromJson(businessListJson, businessType)

        val geocoder = Geocoder(this, Locale.getDefault())
        allBusinesses.clear()

        for (business in businesses) {
            val country = getStateName(business.location, geocoder)
            allBusinesses.add(Pair(business, country))
        }

        businessList.addAll(allBusinesses)
        businessAdapter.notifyDataSetChanged()
    }

    private fun getStateName(location: LatLng, geocoder: Geocoder): String {
        return try {
            val addressList = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (addressList!!.isNotEmpty()) {
                val address = addressList[0]
                val adminArea = address.adminArea ?: "Unknown State"
                val subAdminArea = address.subAdminArea ?: ""
                if (subAdminArea.isNotEmpty()) "$adminArea, $subAdminArea" else adminArea
            } else {
                "Unknown State"
            }
        } catch (e: Exception) {
            Log.e("ListBusinessActivity", "Error getting state name", e)
            "Unknown State"
        }
    }

//    private fun filterBusinesses(criteria: String) {
//        val filteredBusinesses = if (criteria.isEmpty()) {
//            allBusinesses
//        } else {
//            val criteriaList = criteria.split(", ").map { it.trim() }
//            allBusinesses.filter { (business, _) ->
//                criteriaList.any { criterion ->
//                    business.kitchenType.contains(criterion, ignoreCase = true)
//                }
//            }
//        }
//        businessList.clear()
//        businessList.addAll(filteredBusinesses)
//        businessAdapter.notifyDataSetChanged()
//    }
}
