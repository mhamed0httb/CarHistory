package com.cheersapps.carhistory.feature.onBoarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.cheersapps.carhistory.R
import kotlinx.android.synthetic.main.item_onboarding.view.*

class PagerAdapter : PagerAdapter() {

    private val items: ArrayList<Int> = ArrayList()

    init {
        val pages = arrayOf(R.mipmap.onboarding_1, R.mipmap.onboarding_2, R.mipmap.onboarding_3)
        items.addAll(pages.toList())
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`

    override fun getCount(): Int = items.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = LayoutInflater.from(container.context).inflate(R.layout.item_onboarding, container, false)
        itemView.item_onboarding_img.setImageResource(items.elementAt(position))
        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    fun changeAll(items: Collection<Int>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}