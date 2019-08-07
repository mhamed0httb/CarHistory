package com.cheersapps.carhistory.feature.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.recyclerView.BaseAdapter
import com.cheersapps.carhistory.data.entity.Repair

class UpcomingAdapter: BaseAdapter<Repair, UpcomingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_upcoming, parent, false)
        return UpcomingViewHolder((view))
    }

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        holder.bindView(adapterItems[position])
    }
}