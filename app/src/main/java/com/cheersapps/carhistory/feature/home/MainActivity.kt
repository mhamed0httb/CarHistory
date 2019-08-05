package com.cheersapps.carhistory.feature.home

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.activity.BaseActivity
import com.cheersapps.carhistory.data.entity.Repair
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val listAdapter: ListAdapter by lazy {
        ListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initListRepairs()
        prepareFakeData()
    }

    private fun prepareFakeData() {
        val list = ArrayList<Repair>()
        list.add(Repair())
        list.add(Repair())
        list.add(Repair())
        list.add(Repair())
        list.add(Repair())
        list.add(Repair())
        list.add(Repair())
        list.add(Repair())
        list.add(Repair())
        list.add(Repair())
        list.add(Repair())
        listAdapter.changeAll(list)
    }

    private fun initListRepairs() {
        main_rcv_list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        main_rcv_list.adapter = listAdapter
    }
}
