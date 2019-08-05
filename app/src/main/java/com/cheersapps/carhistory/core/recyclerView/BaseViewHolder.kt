package com.cheersapps.carhistory.core.recyclerView

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseViewHolder<D>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bindView(
            item: D
    )
}