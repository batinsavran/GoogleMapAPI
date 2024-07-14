package com.example.learngit.business

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.learngit.R
import com.example.learngit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mapsActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapsActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val address = data?.getStringExtra("address") ?: "N/A"
                val locality = data?.getStringExtra("locality") ?: "N/A"
                val state = data?.getStringExtra("state") ?: "N/A"
                val country = data?.getStringExtra("country") ?: "N/A"
                val postalCode = data?.getStringExtra("postalCode") ?: "N/A"

                binding.businessesCurrentLocationTextView.text = getString(
                    R.string.location_info,
                    address,
                    locality,
                    state,
                    country,
                    postalCode
                )
            }
        }

        binding.businessesCurrentLocationTextView.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            mapsActivityResultLauncher.launch(intent)
        }
    }
}
