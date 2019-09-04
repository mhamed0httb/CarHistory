package com.cheersapps.carhistory.feature.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.recyclerView.BaseAdapter
import com.cheersapps.carhistory.core.recyclerView.BaseViewHolder
import com.cheersapps.carhistory.data.entity.Repair
import kotlinx.android.synthetic.main.item_repair_selected.view.*

class ListAdapter(private val listener: OnHomeInteractionListener?,
                  val deleteListener: (item: Repair, position: Int) -> Unit)
    : BaseAdapter<Repair, BaseViewHolder<Repair>>() {

    private var selectedCard: Int = -1
    private var previousSelectedCard: Int = -1

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
        holder.bindView(adapterItems[position])
        when (holder) {
            is ListViewHolder -> {
                holder.initCardView(position == selectedCard)
                holder.setListener(getItemClickListener(holder))
            }
            is SelectedListViewHolder -> {
                val clickListener = View.OnClickListener {
                    selectedCard = -1

                    deleteListener(adapterItems[position], position)
                }

                val detailsClickListener = View.OnClickListener {
                    listener?.detailsRepair(adapterItems[position], holder.itemView.item_repair_img)
                }

                holder.setDeleteListener(clickListener)
                holder.setListener(getItemClickListener(holder))
                holder.setDetailsListener(detailsClickListener)
            }
        }
    }

    private fun getItemClickListener(holder: BaseViewHolder<Repair>): View.OnClickListener = View.OnClickListener {
        selectedCard = if (selectedCard == holder.adapterPosition) {
            previousSelectedCard = -1
            -1
        } else {
            previousSelectedCard = if (previousSelectedCard == -1) {
                holder.adapterPosition
            } else {
                notifyItemChanged(previousSelectedCard)
                holder.adapterPosition
            }
            holder.adapterPosition
        }
        notifyItemChanged(holder.adapterPosition)
    }
}