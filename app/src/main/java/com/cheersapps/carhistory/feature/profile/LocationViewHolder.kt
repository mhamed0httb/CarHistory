package com.cheersapps.carhistory.feature.profile

import android.view.View
import com.cheersapps.carhistory.core.recyclerView.BaseViewHolder
import com.cheersapps.carhistory.data.entity.Location
import kotlinx.android.synthetic.main.item_location.view.*

class LocationViewHolder(view: View): BaseViewHolder<Location>(view) {


    override fun bindView(item: Location) {
        itemView.item_location_txv_name.text = item.name
    }

    fun setDeleteListener(listener: View.OnClickListener) {
        itemView.item_location_btn_delete.setOnClickListener(listener)
    }
}