package com.cheersapps.carhistory.feature.home

import android.view.View
import com.cheersapps.carhistory.core.recyclerView.BaseViewHolder
import com.cheersapps.carhistory.data.entity.Repair
import kotlinx.android.synthetic.main.item_repair_selected.view.*

class SelectedListViewHolder(view: View): BaseViewHolder<Repair>(view) {


    override fun bindView(item: Repair) {

    }

    fun setDeleteListener(clickListener: View.OnClickListener) {
        itemView.item_repair_txv_delete.setOnClickListener(clickListener)
    }
}