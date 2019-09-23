package com.cheersapps.carhistory.core.recyclerView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.os.Handler
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<D, V : BaseViewHolder<D>> : androidx.recyclerview.widget.RecyclerView.Adapter<V>() {

    protected var adapterItems: MutableList<D> = ArrayList()
        private set

    private var rcv: androidx.recyclerview.widget.RecyclerView? = null

    private var isAdapterEmpty: MutableLiveData<Boolean> = MutableLiveData()
    fun observeAdapterCount(): LiveData<Boolean> = isAdapterEmpty

    init {
        isAdapterEmpty.value = null
    }

    private fun clearData() {
        if (this.adapterItems.isNotEmpty())
            this.adapterItems.clear()
        else
            this.adapterItems = ArrayList()
    }

    fun deleteAll() {
        clearData()
        notifyDataSetChanged()
        isAdapterEmpty.postValue(itemCount == 0)
    }

    open fun changeAll(items: List<D>) {
        clearData()
        if (items.isNotEmpty()) {
            this.adapterItems.addAll(items)
        }
        notifyDataSetChanged()
        isAdapterEmpty.postValue(itemCount == 0)
    }

    fun changeItem(item: D) {
        item?.let {
            val index = this.adapterItems.indexOf(it)
            if (index != -1) {
                changeItemAt(index, it)
            }
        }
    }

    fun changeItemAt(position: Int, item: D) {
        item?.let {
            if (position < this.adapterItems.size) {
                this.adapterItems[position] = item
                notifyItemChanged(position)
                isAdapterEmpty.postValue(itemCount == 0)
            }
        }
    }

    fun removeItemAt(position: Int) {
        if (position < this.adapterItems.size) {
            this.adapterItems.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, this.adapterItems.size)
            isAdapterEmpty.postValue(itemCount == 0)
        }
    }

    fun removeItem(item: D) {
        item?.let {
            val index = this.adapterItems.indexOf(it)
            if (index != -1) {
                removeItemAt(index)
            }
        }
    }

    fun removeItemsFrom(positionStart: Int, count: Int) {
        if (positionStart < this.adapterItems.size && count <= (this.adapterItems.size - positionStart)) {
            val range = positionStart + count - 1
            for (i in range downTo positionStart) {
                this.adapterItems.removeAt(i)
            }
            notifyItemRangeChanged(positionStart, count)
            isAdapterEmpty.postValue(itemCount == 0)
        }
    }

    fun insertItem(item: D) {
        item?.let {
            this.adapterItems.add(it)
            notifyItemInserted(this.adapterItems.size - 1)
            isAdapterEmpty.postValue(itemCount == 0)
        }
    }

    fun insertItemAt(position: Int, item: D) {
        item?.let {
            if (position <= this.adapterItems.size) {
                this.adapterItems.add(position, item)
                notifyItemInserted(position)
                notifyItemRangeChanged(position, this.adapterItems.size)
                isAdapterEmpty.postValue(itemCount == 0)

                Handler().postDelayed({
                    rcv?.smoothScrollToPosition(position)
                }, 500)
            }
        }
    }

    fun insertItemAtTop(item: D) {
        item?.let {
            insertItemAt(0, it)
        }
    }

    fun insertItemAtBottom(item: D) {
        item?.let {
            insertItemAt(this.adapterItems.size, it)
        }
    }

    fun insertItemsFrom(positionStart: Int, items: List<D>) {
        if (positionStart < this.adapterItems.size) {
            this.adapterItems.addAll(positionStart, items)
            notifyItemRangeChanged(positionStart, this.adapterItems.size)
            isAdapterEmpty.postValue(itemCount == 0)
        }
    }

    fun replaceItemsFrom(position: Int, items: List<D>) {
        if (position <= this.adapterItems.size) {
            for (i in adapterItems.size - 1 downTo position step 1) {
                adapterItems.removeAt(i)
                notifyItemRemoved(i)
            }
            adapterItems.addAll(position, items)
            notifyItemRangeChanged(position, this.adapterItems.size)
            isAdapterEmpty.postValue(itemCount == 0)
        }
    }

    fun replaceItemFrom(position: Int, item: D) {
        if (position <= this.adapterItems.size) {
            for (i in adapterItems.size - 1 downTo position step 1) {
                adapterItems.removeAt(i)
                notifyItemRemoved(i)
            }
            adapterItems.add(position, item)
            notifyItemRangeChanged(position, this.adapterItems.size)
            isAdapterEmpty.postValue(itemCount == 0)
        }
    }

    fun replaceItemAt(position: Int, item: D) {
        if (position <= this.adapterItems.size) {
            adapterItems.set(position, item)
            notifyItemRangeChanged(position, this.adapterItems.size)
            isAdapterEmpty.postValue(itemCount == 0)
        }
    }

    fun insertItemsAtBottom(items: List<D>) {
        val positionStart = this.adapterItems.size
        this.adapterItems.addAll(positionStart, items)
        notifyItemRangeChanged(positionStart, this.adapterItems.size)
        isAdapterEmpty.postValue(itemCount == 0)
    }

    fun getItemAt(position: Int): D? {
        if (position < this.adapterItems.size) {
            return this.adapterItems[position]
        }
        return null
    }

    fun getItemPosition(item: D): Int {
        return adapterItems.indexOf(item)
    }

    fun hasData(): Boolean {
        return !adapterItems.isEmpty()
    }

    override fun getItemCount(): Int {
        return this.adapterItems.size
    }

    override fun onAttachedToRecyclerView(recyclerView: androidx.recyclerview.widget.RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        rcv = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: androidx.recyclerview.widget.RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        rcv = null
    }
}