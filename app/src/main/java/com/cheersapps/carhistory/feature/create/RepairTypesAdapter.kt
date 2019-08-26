package com.cheersapps.carhistory.feature.create

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.recyclerView.BaseAdapter
import com.cheersapps.carhistory.data.entity.RepairType

class RepairTypesAdapter: BaseAdapter<RepairType, RepairTypesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepairTypesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repair_type, parent, false)
        return RepairTypesViewHolder((view))
    }

    override fun onBindViewHolder(holder: RepairTypesViewHolder, position: Int) {
        holder.bindView(adapterItems[position])
    }
}