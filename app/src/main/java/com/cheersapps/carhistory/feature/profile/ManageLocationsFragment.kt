package com.cheersapps.carhistory.feature.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.fragment.BaseFragment
import com.cheersapps.carhistory.data.entity.Location
import com.cheersapps.carhistory.feature.home.HomeViewModel
import kotlinx.android.synthetic.main.fragment_manage_locations.view.*


class ManageLocationsFragment : BaseFragment() {

    private val locationsAdapter: LocationsAdapter by lazy {
        LocationsAdapter(this::deleteLocation)
    }

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this)[HomeViewModel::class.java]
    }

    private var isObserved = false


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_manage_locations, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers(view)
        initLocations(view)
        initClicks(view)
    }


    private fun initClicks(view: View) {
        view.locations_btn_submit.setOnClickListener {
            val name = view.locations_etx_location.text
            if (name.isNullOrEmpty()) {
                view.locations_etx_layout_location.error = getString(R.string.empty_field)
                return@setOnClickListener
            }
            view.locations_etx_layout_location.isErrorEnabled = false


            homeViewModel.insertLocation(Location(name.toString()))
        }
    }

    private fun initObservers(view: View) {
        locationsAdapter.observeAdapterCount().observe(this, Observer { nullable ->
            nullable?.let { isEmpty ->
                toggleEmptyView(isEmpty)
            }
        })

        homeViewModel.getLocations().observe(this, Observer { nullable ->
            nullable?.let { locations ->
                if (!isObserved) {
                    locationsAdapter.changeAll(locations)
                    isObserved = true
                }

            }
        })

        homeViewModel.observeDeleteLocation().observe(this, Observer { nullable ->
            nullable?.let { position ->
                if (position == -1) {
                } else {
                    locationsAdapter.removeItemAt(position)
                }
            }
        })

        homeViewModel.observeInsertLocation().observe(this, Observer { nullable ->
            nullable?.let { location ->
                locationsAdapter.insertItem(location)
            }
        })
    }

    private fun initLocations(view: View) {
        view.locations_rcv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        view.locations_rcv.adapter = locationsAdapter
    }

    private fun deleteLocation(location: Location, position: Int) {
        homeViewModel.deleteLocation(location, position)
    }

    private fun toggleEmptyView(isEmpty: Boolean) {
        if (isEmpty) {
            view?.locations_animation_empty?.visibility = View.VISIBLE
            view?.locations_rcv?.visibility = View.GONE
        } else {
            view?.locations_animation_empty?.visibility = View.GONE
            view?.locations_rcv?.visibility = View.VISIBLE
        }
    }


    companion object {

        @JvmStatic
        fun newInstance() = ManageLocationsFragment()
    }
}
