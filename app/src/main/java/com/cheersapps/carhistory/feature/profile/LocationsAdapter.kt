package com.cheersapps.carhistory.feature.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.recyclerView.BaseAdapter
import com.cheersapps.carhistory.data.entity.Location

class LocationsAdapter(private val deleteFun: (item: Location, position: Int) -> Unit) : BaseAdapter<Location, LocationViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_location, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bindView(adapterItems[position])

        val deleteClickListener = View.OnClickListener {
            deleteFun(adapterItems[position], position)
        }
        holder.setDeleteListener(deleteClickListener)
    }
}