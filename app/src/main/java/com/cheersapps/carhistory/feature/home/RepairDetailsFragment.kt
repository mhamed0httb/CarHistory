package com.cheersapps.carhistory.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.common.constant.Constants.ARG_REPAIR
import com.cheersapps.carhistory.core.fragment.BaseFragment
import com.cheersapps.carhistory.data.entity.Repair
import com.cheersapps.carhistory.utils.DateUtils
import kotlinx.android.synthetic.main.fragment_repair_details.view.*

class RepairDetailsFragment : BaseFragment() {

    private var repair: Repair? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            repair = it.getSerializable(ARG_REPAIR) as Repair
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_repair_details, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repair?.createdAt?.let {
            val builder = StringBuilder()
            builder.append(DateUtils.timestampToDateString(it))
            builder.append(" ")
            builder.append(DateUtils.timestampToTimeString(it))
            view.details_txv_created_at.text = builder.toString()
        }


    }

    companion object {

        @JvmStatic
        fun newInstance(repair: Repair) = RepairDetailsFragment().apply {
            val bundle = Bundle()
            bundle.putSerializable(ARG_REPAIR, repair)
            this.arguments = bundle
        }
    }
}
