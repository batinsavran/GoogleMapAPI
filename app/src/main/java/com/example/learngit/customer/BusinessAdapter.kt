package com.example.learngit.customer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learngit.R

class BusinessAdapter(private val businesses: List<Pair<Business, String>>) :
    RecyclerView.Adapter<BusinessAdapter.BusinessViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_business, parent, false)
        return BusinessViewHolder(view)
    }

    override fun onBindViewHolder(holder: BusinessViewHolder, position: Int) {
        val (business, state) = businesses[position]
        holder.bind(business, state)
    }

    override fun getItemCount() = businesses.size

    class BusinessViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val businessImage: ImageView = itemView.findViewById(R.id.businessPhotoImageView)
        private val businessName: TextView = itemView.findViewById(R.id.businessNameTextView)
        private val kitchenType: TextView = itemView.findViewById(R.id.kitchenTypeTextView)
        private val locationText: TextView = itemView.findViewById(R.id.locationTextView)

        fun bind(business: Business, state: String) {
            businessImage.setImageResource(business.imageResId)
            businessName.text = business.name
            kitchenType.text = business.kitchenType
            locationText.text = state
        }
    }
}
