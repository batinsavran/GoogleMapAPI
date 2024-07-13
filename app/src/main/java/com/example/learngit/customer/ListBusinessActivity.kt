package com.example.learngit.customer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.example.learngit.R
import com.example.learngit.databinding.ActivityListBusinessBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class ListBusinessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBusinessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBusinessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mapButton.setOnClickListener {
            val intent = Intent(this, LastActivity::class.java)
            startActivity(intent)
        }

        binding.filter.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.bottomsheet_filter, null)
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }
}