package com.cheersapps.carhistory.feature.details

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.transition.TransitionInflater
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.common.constant.Constants.ARG_REPAIR
import com.cheersapps.carhistory.core.fragment.BaseFragment
import com.cheersapps.carhistory.data.entity.Repair
import com.cheersapps.carhistory.data.entity.RepairType
import com.cheersapps.carhistory.utils.DateUtils
import kotlinx.android.synthetic.main.fragment_repair_details_2.view.*

class RepairDetailsFragment : BaseFragment() {

    private var repair: Repair? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }

        arguments?.let {
            repair = it.getSerializable(ARG_REPAIR) as Repair
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_repair_details_2, container, false)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repair?.icon?.let {
            view.details_img_type.setImageResource(it)
        }

        repair?.type?.let {
            view.details_txv_type.text = getString(RepairType.valueOf(it).title)
        }

        repair?.location?.let {
            view.details_txv_location.text = it
        }

        repair?.date?.let {
            view.details_txv_date.text = DateUtils.timestampToDateString(it)
        }

        repair?.mileage?.let {
            val mileageBuilder = StringBuilder()
            mileageBuilder.append(it)
            mileageBuilder.append(" KM")
            view.details_txv_mileage.text = mileageBuilder.toString()
        }

        repair?.amount?.let {
            val amountBuilder = StringBuilder()
            amountBuilder.append(it)
            amountBuilder.append(" DT")
            view.details_txv_price.text = amountBuilder.toString()
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
