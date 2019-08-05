package com.cheersapps.carhistory.feature.home

import android.support.v4.content.ContextCompat
import android.view.View
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.recyclerView.BaseViewHolder
import com.cheersapps.carhistory.data.entity.Repair
import kotlinx.android.synthetic.main.item_repair.view.*

class ListViewHolder(view: View) : BaseViewHolder<Repair>(view) {


    override fun bindView(item: Repair) {
       /*
        itemView.setOnClickListener {
            itemView.item_repair_root.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.deepBlue))
            itemView.item_repair_card.radius = 15f
        }
        */
    }

    fun initCardView(isSelected: Boolean = false) {
        if (isSelected) {
            itemView.item_repair_root.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.deepBlue))
            itemView.item_repair_card.radius = 15f
        } else {
            itemView.item_repair_root.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.darkBlue))
            itemView.item_repair_card.radius = 0f
        }
    }

    fun setListener(clickListener: View.OnClickListener) {
        itemView.setOnClickListener(clickListener)
    }
}