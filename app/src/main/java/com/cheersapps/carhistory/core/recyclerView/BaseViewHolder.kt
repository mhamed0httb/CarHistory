package com.cheersapps.carhistory.core.recyclerView

import androidx.recyclerview.widget.RecyclerView
import android.view.View

abstract class BaseViewHolder<D>(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

    abstract fun bindView(
            item: D
    )
}