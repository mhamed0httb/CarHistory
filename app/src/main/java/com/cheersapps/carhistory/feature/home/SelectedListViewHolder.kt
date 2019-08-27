package com.cheersapps.carhistory.feature.home

import android.view.View
import com.cheersapps.carhistory.core.recyclerView.BaseViewHolder
import com.cheersapps.carhistory.data.entity.Repair
import com.cheersapps.carhistory.data.entity.RepairType
import com.cheersapps.carhistory.utils.DateUtils
import kotlinx.android.synthetic.main.item_repair_selected.view.*

class SelectedListViewHolder(view: View) : BaseViewHolder<Repair>(view) {


    override fun bindView(item: Repair) {
        item.icon?.let {
            itemView.item_repair_img.setImageResource(it)
        }


        item.type?.let {
            itemView.item_repair_txv_title.text = RepairType.valueOf(it).title
        }

        item.date?.let {
            itemView.item_repair_txv_date.text = DateUtils.timestampToDateString(it)
        }

        item.location?.let {
            itemView.item_repair_txv_place.text = it
        }

    }

    fun setDeleteListener(clickListener: View.OnClickListener) {
        itemView.item_repair_txv_delete.setOnClickListener(clickListener)
    }

    fun setDetailsListener(clickListener: View.OnClickListener) {
        itemView.item_repair_txv_details.setOnClickListener(clickListener)
    }

    fun setListener(clickListener: View.OnClickListener) {
        itemView.setOnClickListener(clickListener)
    }
}