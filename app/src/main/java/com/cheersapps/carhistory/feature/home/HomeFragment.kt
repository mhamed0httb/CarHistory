package com.cheersapps.carhistory.feature.home

import android.content.Context
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
import com.cheersapps.carhistory.data.entity.Repair
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : BaseFragment() {

    private var listener: OnHomeInteractionListener? = null
    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this)[HomeViewModel::class.java]
    }

    private val listAdapter: ListAdapter by lazy {
        ListAdapter(listener)
    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRcvRepairs(view)
        homeViewModel.getRepairs().observe(this, Observer {
            listAdapter.changeAll(it)
        })
    }

    private fun initRcvRepairs(view: View) {
        view.home_rcv_repairs.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        view.home_rcv_repairs.adapter = listAdapter
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
        fun detailsRepair(repair: Repair, sharedView: View)
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
