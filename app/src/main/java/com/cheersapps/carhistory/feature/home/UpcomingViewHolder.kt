package com.cheersapps.carhistory.feature.home

import android.view.View
import com.cheersapps.carhistory.core.recyclerView.BaseViewHolder
import com.cheersapps.carhistory.data.entity.Repair
import kotlinx.android.synthetic.main.item_upcoming.view.*

class UpcomingViewHolder(view: View): BaseViewHolder<Repair>(view) {

    override fun bindView(item: Repair) {
        itemView.item_upcoming_img.setImageResource(item.icon!!)
        itemView.item_upcoming_txv_title.text = item.body
        itemView.item_upcoming_txv_date.text = item.date
    }
}