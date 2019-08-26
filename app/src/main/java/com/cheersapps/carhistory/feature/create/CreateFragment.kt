package com.cheersapps.carhistory.feature.create

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.fragment.BaseFragment
import com.cheersapps.carhistory.data.entity.Repair
import com.cheersapps.carhistory.data.entity.RepairType
import com.cheersapps.carhistory.feature.home.HomeViewModel
import com.cheersapps.carhistory.utils.DateUtils
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_create.view.*
import java.util.*

class CreateFragment : BaseFragment() {

    private var listener: OnCreateInteractionListener? = null

    private val repairTypesAdapter: RepairTypesAdapter by lazy {
        RepairTypesAdapter()
    }
    private var date: Date = DateUtils.currentFullDate()

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this)[HomeViewModel::class.java]
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_create, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListTypes(view)
        initClicks(view)
    }

    private fun initClicks(view: View) {
        view.create_btn_submit.setOnClickListener {
            if (repairTypesAdapter.getSelectedItem() == null) {
                Toast.makeText(context!!, "choose type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(context!!, "Loading...", Toast.LENGTH_LONG).show()

            val repair = Repair()
            repair.body = view.create_etx_body.text.toString()
            repair.date = this.date.time
            repair.type = repairTypesAdapter.getSelectedItem()?.name

            homeViewModel.insertRepair(repair)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : CompletableObserver {
                        override fun onComplete() {
                            Toast.makeText(context!!, "Done", Toast.LENGTH_SHORT).show()
                        }

                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onError(e: Throwable) {
                            Toast.makeText(context!!, "Error", Toast.LENGTH_SHORT).show()
                        }

                    })

        }

        view.create_calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            this.date = calendar.time
        }
    }


    private fun initListTypes(view: View) {
        view.create_rcv_list_types.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                context,
                RecyclerView.HORIZONTAL,
                false
        )
        view.create_rcv_list_types.adapter = repairTypesAdapter

        repairTypesAdapter.changeAll(RepairType.values().toList())
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
