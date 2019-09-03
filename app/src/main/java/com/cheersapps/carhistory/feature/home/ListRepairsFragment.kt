package com.cheersapps.carhistory.feature.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.fragment.BaseFragment
import com.cheersapps.carhistory.data.entity.Repair
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list_repairs.view.*

class ListRepairsFragment : BaseFragment() {


    private var listener: OnRepairsInteractionListener? = null
    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this)[HomeViewModel::class.java]
    }

    private val listAdapter: ListAdapter by lazy {
        ListAdapter(listener, this::deleteRepair)
    }

    private var isObserved: Boolean = false



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_list_repairs, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRcvRepairs(view)
        initObservers()
    }


    private fun deleteRepair(repair: Repair, position: Int) {
        homeViewModel.deleteRepair(repair)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onComplete() {
                        listAdapter.removeItemAt(position)
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(context!!, "Error", Toast.LENGTH_SHORT).show()
                    }

                })
    }

    private fun initObservers() {
        homeViewModel.getRepairs().observe(this, Observer {
            if (!isObserved) {
                listAdapter.changeAll(it)
                isObserved = true
            }
        })

        listAdapter.observeAdapterCount().observe(this, Observer { nullable ->
            nullable?.let { isEmpty ->
                toggleEmptyView(isEmpty)
            }
        })
    }

    private fun initRcvRepairs(view: View) {
        view.home_rcv_repairs.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        view.home_rcv_repairs.adapter = listAdapter
    }


    private fun toggleEmptyView(isEmpty: Boolean) {
        if (isEmpty) {
            view?.home_rcv_repairs?.visibility = View.GONE
            view?.home_animation_empty?.visibility = View.VISIBLE
        } else {
            view?.home_rcv_repairs?.visibility = View.VISIBLE
            view?.home_animation_empty?.visibility = View.GONE
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnRepairsInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnRepairsInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnRepairsInteractionListener {
        fun detailsRepair(repair: Repair, sharedView: View)
    }

    companion object {

        @JvmStatic
        fun newInstance() = ListRepairsFragment()
    }
}
