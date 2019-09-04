package com.cheersapps.carhistory.feature.home

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProviders
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.common.constant.Constants
import com.cheersapps.carhistory.core.activity.BaseActivity
import com.cheersapps.carhistory.core.activity.BaseActivityExtension.replaceFragmentSafely
import com.cheersapps.carhistory.core.activity.BaseActivityExtension.showMessage
import com.cheersapps.carhistory.data.entity.Repair
import com.cheersapps.carhistory.data.entity.RepairType
import com.cheersapps.carhistory.feature.create.CreateFragment
import com.cheersapps.carhistory.feature.details.RepairDetailsFragment
import com.cheersapps.carhistory.feature.login.LoginActivity
import com.cheersapps.carhistory.feature.profile.ManageLocationsFragment
import com.cheersapps.carhistory.feature.profile.ProfileFragment
import com.cheersapps.carhistory.feature.statistics.StatisticsFragment
import com.cheersapps.carhistory.utils.NavigationUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home_page.*

class HomePageActivity : BaseActivity(), OnHomeInteractionListener {

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this)[HomeViewModel::class.java]
    }


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        supportFragmentManager.popBackStack()
        when (item.itemId) {
            R.id.navigation_home -> {
                updateToolbar(getString(R.string.home))
                replaceFragmentSafely(
                        R.id.home_container,
                        HomeFragment.newInstance(),
                        HomeFragment::class.java.simpleName,
                        false
                )
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                updateToolbar(getString(R.string.title_create))
                replaceFragmentSafely(
                        R.id.home_container,
                        CreateFragment.newInstance(),
                        CreateFragment::class.java.simpleName,
                        false
                )
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                updateToolbar(getString(R.string.profile))
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

        val ext = intent.extras?.get("navigation_extra_key")
        ext?.let {
            home_nav_view.selectedItemId = R.id.navigation_profile
            return@onCreate
        }

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
        updateToolbar(getString(R.string.home))
    }

    private fun initBottomNavigation() {
        home_nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun updateToolbar(title: String, isNavigationEnabled: Boolean = false) {
        home_toolbar_txv_title.text = title
        toggleNavigation(isNavigationEnabled)
    }

    private fun toggleNavigation(isEnabled: Boolean) {
        if (isEnabled) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            home_toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }


    override fun onBackPressed() {
        when (supportFragmentManager.findFragmentById(R.id.home_container)) {
            is RepairDetailsFragment -> {
                updateToolbar(getString(R.string.title_repairs), true)
            }
            is ManageLocationsFragment -> {
                updateToolbar(getString(R.string.profile))
            }
            is ListRepairsFragment -> {
                updateToolbar(getString(R.string.home))
            }
        }
        super.onBackPressed()
    }


    /**
     * OnHomeInteractionListener implementation
     */
    override fun navigateTo(fragmentName: String) {
        when (fragmentName) {
            Constants.homeNavigation[0] -> {
                updateToolbar(getString(R.string.title_repairs), true)
                replaceFragmentSafely(
                        R.id.home_container,
                        ListRepairsFragment.newInstance(),
                        ListRepairsFragment::class.java.simpleName,
                        true
                )

            }
            Constants.homeNavigation[1] -> {
                home_nav_view.selectedItemId = R.id.navigation_dashboard
            }
            Constants.homeNavigation[2] -> {
                home_nav_view.selectedItemId = R.id.navigation_profile
            }
            Constants.homeNavigation[3] -> {
                updateToolbar(getString(R.string.title_dashboard), true)
                replaceFragmentSafely(
                        R.id.home_container,
                        StatisticsFragment.newInstance(),
                        StatisticsFragment::class.java.simpleName,
                        true
                )
            }
        }
    }

    override fun detailsRepair(repair: Repair, sharedView: View) {
        repair.type?.let {
            updateToolbar(getString(RepairType.valueOf(it).title), true)
        }

        val transitionName = ViewCompat.getTransitionName(sharedView)
        transitionName?.let {
            supportFragmentManager
                    .beginTransaction()
                    .addSharedElement(sharedView, it)
                    .replace(
                            R.id.home_container,
                            RepairDetailsFragment.newInstance(repair),
                            RepairDetailsFragment::class.java.simpleName
                    )
                    .addToBackStack(RepairDetailsFragment::class.java.simpleName)
                    .commit()
        }

    }

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

    override fun manageLocations() {
        updateToolbar(getString(R.string.manage_places), true)


        replaceFragmentSafely(
                R.id.home_container,
                ManageLocationsFragment.newInstance(),
                ManageLocationsFragment::class.java.simpleName,
                true
        )
    }

}
