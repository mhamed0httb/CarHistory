package com.cheersapps.carhistory.feature.home

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.activity.BaseActivity
import com.cheersapps.carhistory.data.entity.Repair
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val listAdapter: ListAdapter by lazy {
        ListAdapter()
    }

    private val upcomingAdapter: UpcomingAdapter by lazy {
        UpcomingAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()

        initListRepairs()
        initListUpcoming()
        prepareFakeData()


    }

    private fun initToolbar() {
        setSupportActionBar(main_toolbar)
        supportActionBar?.title = ""
        main_toolbar_txv_title.text = "Home"
    }

    private fun initListUpcoming() {
        main_rcv_list_recent.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            this,
            androidx.recyclerview.widget.RecyclerView.HORIZONTAL,
            false
        )
        main_rcv_list_recent.adapter = upcomingAdapter
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
        upcomingAdapter.changeAll(list)
    }

    private fun initListRepairs() {
        main_rcv_list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            this,
            androidx.recyclerview.widget.RecyclerView.VERTICAL,
            false
        )
        main_rcv_list.adapter = listAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val itemId = item?.itemId
        itemId?.let {
            when(it) {
                R.id.menu_main_add -> {

                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
