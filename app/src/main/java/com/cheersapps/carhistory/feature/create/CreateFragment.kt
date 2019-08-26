package com.cheersapps.carhistory.feature.create

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.fragment.BaseFragment
import com.cheersapps.carhistory.data.entity.RepairType
import com.cheersapps.carhistory.utils.DateUtils
import kotlinx.android.synthetic.main.fragment_create.view.*

class CreateFragment : BaseFragment() {

    private var listener: OnCreateInteractionListener? = null

    private val repairTypesAdapter: RepairTypesAdapter by lazy {
        RepairTypesAdapter()
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_create, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val date = DateUtils.currentFullDateTimestamp()
        val dd = DateUtils.timestampToDateString(date)
        val hh = DateUtils.timestampToTimeString(date)

        initListUpcoming(view)
        repairTypesAdapter.changeAll(RepairType.values().toList())
    }



    private fun initListUpcoming(view: View) {
        view.create_rcv_list_recent.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                context,
                RecyclerView.HORIZONTAL,
                false
        )
        view.create_rcv_list_recent.adapter = repairTypesAdapter
        return
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCreateInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnCreateInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnCreateInteractionListener {

    }

    companion object {

        @JvmStatic
        fun newInstance() = CreateFragment()
    }
}
