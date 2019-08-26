package com.cheersapps.carhistory.feature.home

import androidx.core.content.ContextCompat
import android.view.View
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.recyclerView.BaseViewHolder
import com.cheersapps.carhistory.data.entity.Repair
import com.cheersapps.carhistory.data.entity.RepairType
import com.cheersapps.carhistory.utils.DateUtils
import kotlinx.android.synthetic.main.item_repair.view.*

class ListViewHolder(view: View) : BaseViewHolder<Repair>(view) {


    override fun bindView(item: Repair) {
       /*
        itemView.setOnClickListener {
            itemView.item_repair_root.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.deepBlue))
            itemView.item_repair_card.radius = 15f
        }
        */

        item.icon?.let {
            itemView.item_repair_img.setImageResource(it)
        }

        item.type?.let {
            itemView.item_repair_txv_title.text = RepairType.valueOf(it).title
        }

        item.date?.let {
            itemView.item_repair_txv_date.text = DateUtils.timestampToDateString(it)
        }




    }

    fun initCardView(isSelected: Boolean = false) {
        if (isSelected) {
            itemView.item_repair_root.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.deepBlue))
            itemView.item_repair_card.radius = 15f
        } else {
            itemView.item_repair_root.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.darkBlue))
            itemView.item_repair_card.radius = 15f
        }
    }

    fun setListener(clickListener: View.OnClickListener) {
        itemView.setOnClickListener(clickListener)
    }
}