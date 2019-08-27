package com.cheersapps.carhistory.feature.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.recyclerView.BaseAdapter
import com.cheersapps.carhistory.core.recyclerView.BaseViewHolder
import com.cheersapps.carhistory.data.entity.Repair

class ListAdapter : BaseAdapter<Repair, BaseViewHolder<Repair>>() {

    private var selectedCard: Int = -1

    override fun getItemViewType(position: Int): Int {
        return if (position == selectedCard)
            0
        else
            1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Repair> {
        return when (viewType) {
            0 -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repair_selected, parent, false)
                SelectedListViewHolder((view))
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repair, parent, false)
                ListViewHolder((view))
            }
        }

    }

    override fun onBindViewHolder(holder: BaseViewHolder<Repair>, position: Int) {
        when (holder) {
            is ListViewHolder -> {
                holder.initCardView(position == selectedCard)
                holder.bindView(adapterItems[position])
                val clickListener = View.OnClickListener {
                    selectedCard = holder.adapterPosition
                    notifyDataSetChanged()
                }
                holder.setListener(clickListener)
            }
            is SelectedListViewHolder -> {
                holder.bindView(adapterItems[position])
                val clickListener = View.OnClickListener {
                    selectedCard = -1
                    removeItemAt(holder.adapterPosition)
                }
                holder.setDeleteListener(clickListener)
            }
        }
    }
}