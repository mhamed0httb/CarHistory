package com.cheersapps.carhistory.feature.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.activity.BaseActivity
import com.cheersapps.carhistory.data.entity.Repair
import com.cheersapps.carhistory.feature.create.CreateActivity
import com.cheersapps.carhistory.utils.NavigationUtils
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
        main_toolbar_txv_title.text = getString(R.string.home)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        main_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initListUpcoming() {
        main_rcv_list_recent.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                this,
                RecyclerView.HORIZONTAL,
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


        val listUpcoming = ArrayList<Repair>()
        val r1 = Repair()
        val r2 = Repair()
        val r3 = Repair()
        val r4 = Repair()
        r1.icon = R.drawable.ic_oil
        r1.body = "Oil change"
        r1.date = "14/08 2019"
        r2.icon = R.drawable.ic_filter
        r2.body = "Filter change"
        r2.date = "15/08 2019"
        r3.icon = R.drawable.ic_oil
        r3.body = "Oil change"
        r3.date = "20/08 2019"
        r4.icon = R.drawable.ic_cogs
        r4.body = "Maintenance"
        r4.date = "05/09 2019"
        listUpcoming.add(r1)
        listUpcoming.add(r2)
        listUpcoming.add(r3)
        listUpcoming.add(r4)
        upcomingAdapter.changeAll(listUpcoming)
    }

    private fun initListRepairs() {
        main_rcv_list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                this,
                RecyclerView.VERTICAL,
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
            when (it) {
                R.id.menu_main_add -> {
                    NavigationUtils.navigateTo(context = this, activity = CreateActivity::class.java)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
