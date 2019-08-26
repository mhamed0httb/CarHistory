package com.cheersapps.carhistory.feature.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.recyclerView.BaseAdapter
import com.cheersapps.carhistory.data.entity.RepairType
import kotlinx.android.synthetic.main.item_repair_type.view.*

class RepairTypesAdapter : BaseAdapter<RepairType, RepairTypesViewHolder>() {

    private var selected: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepairTypesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repair_type, parent, false)
        return RepairTypesViewHolder((view))
    }

    override fun onBindViewHolder(holder: RepairTypesViewHolder, position: Int) {
        holder.bindView(adapterItems[position])
        if (selected == position)
            holder.itemView.item_types_root.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.deepRed))
        else
            holder.itemView.item_types_root.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.deepBlue))

        holder.itemView.setOnClickListener {
            selected = position
            notifyDataSetChanged()
        }
    }

    fun getSelectedItem(): RepairType? {
        if (selected in adapterItems.indices) {
            return adapterItems[selected]
        }
        return null
    }
}