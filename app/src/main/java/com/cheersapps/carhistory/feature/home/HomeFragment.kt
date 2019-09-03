package com.cheersapps.carhistory.feature.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.common.constant.Constants
import com.cheersapps.carhistory.core.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : BaseFragment() {

    private var listener: OnHomeInteractionListener? = null
    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this)[HomeViewModel::class.java]
    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClicks(view)
        initObservers(view)
    }

    private fun initObservers(view: View) {
        homeViewModel.repairRepository.getUpcomingOilChangeMileage().observe(this, Observer {nullable->
            nullable?.let {repair->
                repair.mileage?.takeIf { repair.oilMaxMileage != null }?.apply {
                    val builder = StringBuilder()
                    builder.append(this + repair.oilMaxMileage!!)
                    builder.append(" Km")
                    view.home_txv_upcoming.text = builder.toString()
                }
            }
        })
    }


    private fun initClicks(view: View) {
        view.home_card_repairs.setOnClickListener {
            listener?.navigateTo(Constants.homeNavigation[0])
        }

        view.home_card_new.setOnClickListener {
            listener?.navigateTo(Constants.homeNavigation[1])
        }

        view.home_card_profile.setOnClickListener {
            listener?.navigateTo(Constants.homeNavigation[2])
        }

        view.home_card_dashboard.setOnClickListener {
            listener?.navigateTo(Constants.homeNavigation[3])
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnHomeInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnHomeInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnHomeInteractionListener {
        fun navigateTo(fragmentName: String)
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()
    }


}
