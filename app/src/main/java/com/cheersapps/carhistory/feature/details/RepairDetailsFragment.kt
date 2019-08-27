package com.cheersapps.carhistory.feature.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.common.constant.Constants.ARG_REPAIR
import com.cheersapps.carhistory.core.fragment.BaseFragment
import com.cheersapps.carhistory.data.entity.Repair
import com.cheersapps.carhistory.data.entity.RepairType
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

        repair?.icon?.let {
            view.details_img_icon.setImageResource(it)
        }

        repair?.type?.let {
            view.details_txv_type.text = RepairType.valueOf(it).title
        }

        repair?.location?.let {
            view.details_txv_location.text = it
        }

        repair?.date?.let {
            view.details_txv_date.text = DateUtils.timestampToDateString(it)
        }

        val body = repair?.body
        if (body.isNullOrEmpty()) {
            view.details_txv_body.visibility = View.GONE
            view.details_animation_empty.visibility = View.VISIBLE
        } else {
            view.details_txv_body.text = body
            view.details_txv_body.visibility = View.VISIBLE
            view.details_animation_empty.visibility = View.GONE
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
