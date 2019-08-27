package com.cheersapps.carhistory.feature.home

import android.app.SearchManager
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.activity.BaseActivity
import com.cheersapps.carhistory.core.activity.BaseActivityExtension.replaceFragmentSafely
import com.cheersapps.carhistory.core.activity.BaseActivityExtension.showMessage
import com.cheersapps.carhistory.feature.create.CreateFragment
import com.cheersapps.carhistory.feature.login.LoginActivity
import com.cheersapps.carhistory.feature.profile.ProfileFragment
import com.cheersapps.carhistory.utils.NavigationUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home_page.*

class HomePageActivity : BaseActivity(), ProfileFragment.OnProfileInteractionListener, CreateFragment.OnCreateInteractionListener, HomeFragment.OnHomeInteractionListener {

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this)[HomeViewModel::class.java]
    }


    private var searchView: SearchView? = null

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                home_toolbar_txv_title.text = getString(R.string.home)
                replaceFragmentSafely(
                        R.id.home_container,
                        HomeFragment.newInstance(),
                        HomeFragment::class.java.simpleName,
                        false
                )
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                home_toolbar_txv_title.text = getString(R.string.title_create)
                replaceFragmentSafely(
                        R.id.home_container,
                        CreateFragment.newInstance(),
                        CreateFragment::class.java.simpleName,
                        false
                )
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                home_toolbar_txv_title.text = getString(R.string.profile)
                replaceFragmentSafely(
                        R.id.home_container,
                        ProfileFragment.newInstance(),
                        ProfileFragment::class.java.simpleName,
                        false
                )
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        initToolbar()
        initBottomNavigation()

        replaceFragmentSafely(
                R.id.home_container,
                HomeFragment.newInstance(),
                HomeFragment::class.java.simpleName,
                false
        )
    }

    private fun initToolbar() {
        setSupportActionBar(home_toolbar)
        supportActionBar?.title = ""
        home_toolbar_txv_title.text = getString(R.string.home)
    }

    private fun initBottomNavigation() {
        home_nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView?
        searchView!!.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView!!.maxWidth = Integer.MAX_VALUE
        // listening to search query text change
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // filter recycler view when query submitted
                Toast.makeText(this@HomePageActivity, "onQueryTextSubmit", Toast.LENGTH_SHORT).show()
                invalidateOptionsMenu()
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed
                Toast.makeText(this@HomePageActivity, "onQueryTextChange", Toast.LENGTH_SHORT).show()
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        return if (id == R.id.action_search)
            true
        else
            super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        // close search view on back button pressed
        if (!searchView!!.isIconified) {
            searchView!!.isIconified = true
            return
        }
        super.onBackPressed()
    }

    /**
     * OnProfileInteractionListener implementation
     */
    override fun logout() {
        val dialogListener = DialogInterface.OnClickListener { dialog, _ ->
            homeViewModel.setStayLoggedIn(false)
            NavigationUtils.navigateTo(context = this, finish = true, activity = LoginActivity::class.java)
            dialog.dismiss()
        }
        showMessage(
                getString(R.string.logout),
                getString(R.string.info_logout),
                dialogListener,
                true
        )
    }

}
