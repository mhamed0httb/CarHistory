package com.cheersapps.carhistory.feature.create

import android.view.View
import com.cheersapps.carhistory.core.recyclerView.BaseViewHolder
import com.cheersapps.carhistory.data.entity.RepairType
import kotlinx.android.synthetic.main.item_repair_type.view.*

class RepairTypesViewHolder(view: View): BaseViewHolder<RepairType>(view) {


    override fun bindView(item: RepairType) {
        itemView.item_types_img.setImageResource(item.icon)
        itemView.item_types_txv_title.text = itemView.resources.getString(item.title)
    }
}