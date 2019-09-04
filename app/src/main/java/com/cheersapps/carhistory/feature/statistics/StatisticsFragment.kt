package com.cheersapps.carhistory.feature.statistics

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.fragment.BaseFragment
import com.cheersapps.carhistory.feature.home.OnHomeInteractionListener
import kotlinx.android.synthetic.main.fragment_statistics.view.*


class StatisticsFragment : BaseFragment() {

    private var listener: OnHomeInteractionListener? = null


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_statistics, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.stats_txv_sample
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


    companion object {
        @JvmStatic
        fun newInstance() = StatisticsFragment()
    }
}
