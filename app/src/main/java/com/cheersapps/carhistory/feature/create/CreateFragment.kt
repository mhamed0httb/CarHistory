package com.cheersapps.carhistory.feature.create

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.activity.BaseActivity
import com.cheersapps.carhistory.core.activity.BaseActivityExtension.showMessage
import com.cheersapps.carhistory.core.fragment.BaseFragment
import com.cheersapps.carhistory.data.entity.Location
import com.cheersapps.carhistory.data.entity.Repair
import com.cheersapps.carhistory.data.entity.RepairType
import com.cheersapps.carhistory.feature.home.HomeViewModel
import com.cheersapps.carhistory.feature.home.OnHomeInteractionListener
import com.cheersapps.carhistory.utils.DateUtils
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_create.view.*
import java.util.*
import kotlin.concurrent.schedule

class CreateFragment : BaseFragment() {


    private var listener: OnHomeInteractionListener? = null

    private val repairTypesAdapter: RepairTypesAdapter by lazy {
        RepairTypesAdapter(this::onRepairTypeSelected)
    }
    private var date: Date = DateUtils.currentFullDate()

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this)[HomeViewModel::class.java]
    }

    private var isScrolled = false


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_create, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers(view)
        initListTypes(view)
        initClicks(view)
        view.create_scroll.isSmoothScrollingEnabled = true
    }

    private fun initObservers(view: View) {
        homeViewModel.getLocations().observe(this, androidx.lifecycle.Observer { nullable ->
            nullable?.let { locations ->
                val adapter: ArrayAdapter<Location> = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, locations)
                view.create_spinner_location.adapter = adapter
            }
        })
    }


    private fun initClicks(view: View) {
        view.create_btn_submit.setOnClickListener {
            if (repairTypesAdapter.getSelectedItem() == null) {
                view.create_txv_type.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
                return@setOnClickListener
            }

            val location = (view.create_spinner_location.selectedItem as Location?)?.name
            val mileage = view.create_etx_mileage.text
            val price = view.create_etx_price.text
            val oilType = view.create_etx_oil.text
            val oilMaxMileage = view.create_etx_oil_mileage.text
            if (location.isNullOrEmpty()) {
                view.create_txv_where.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
                return@setOnClickListener
            }


            if (mileage.isNullOrEmpty()) {
                view.create_etx_layout_mileage.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
                view.create_etx_layout_mileage.error = getString(R.string.empty_field)
                return@setOnClickListener
            }
            view.create_etx_layout_mileage.isErrorEnabled = false

            repairTypesAdapter.getSelectedItem()?.takeIf { it == RepairType.OIL_CHANGE }?.apply {
                if (oilType.isNullOrEmpty()) {
                    view.create_etx_layout_oil.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
                    view.create_etx_layout_oil.error = getString(R.string.empty_field)
                    return@setOnClickListener
                }
                view.create_etx_layout_oil.isErrorEnabled = false

                if (oilMaxMileage.isNullOrEmpty()) {
                    view.create_etx_layout_oil_mileage.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
                    view.create_etx_layout_oil_mileage.error = getString(R.string.empty_field)
                    return@setOnClickListener
                }
                view.create_etx_layout_oil_mileage.isErrorEnabled = false
            }

            if (price.isNullOrEmpty()) {
                view.create_etx_layout_price.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
                view.create_etx_layout_price.error = getString(R.string.empty_field)
                return@setOnClickListener
            }
            view.create_etx_layout_price.isErrorEnabled = false

            val priceDouble = price.toString().toDoubleOrNull()
            if (priceDouble == null) {
                view.create_etx_layout_price.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
                view.create_etx_layout_price.error = getString(R.string.invalid_price)
                return@setOnClickListener
            }
            view.create_etx_layout_price.isErrorEnabled = false

            val repair = Repair()
            repair.body = view.create_etx_body.text.toString()
            repair.date = this.date.time
            repair.type = repairTypesAdapter.getSelectedItem()?.name
            repair.location = location.toString()
            repair.mileage = mileage.toString().toLong()
            repair.amount = priceDouble
            repairTypesAdapter.getSelectedItem()?.takeIf { it == RepairType.OIL_CHANGE }?.apply {
                repair.oilType = oilType.toString()
                repair.oilMaxMileage = oilMaxMileage.toString().toLong()
            }

            homeViewModel.insertRepair(repair)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : CompletableObserver {
                        override fun onComplete() {
                            (this@CreateFragment.activity as BaseActivity).showMessage(
                                    getString(R.string.success),
                                    getString(R.string.create_repair_success)
                            )
                            toggleForm(isVisible = false)
                        }

                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onError(e: Throwable) {
                            (this@CreateFragment.activity as BaseActivity).showMessage(
                                    getString(R.string.error),
                                    getString(R.string.create_repair_error)
                            )

                        }

                    })

        }

        view.create_calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            this.date = calendar.time
        }

        view.create_container_upload.setOnClickListener {
            val isPermitted = checkAndRequestPermissions()
            if (isPermitted) {

            }
        }
    }

    private fun checkAndRequestPermissions(): Boolean {
        val camerapermission = ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CAMERA)
        val writepermission = ContextCompat.checkSelfPermission(activity!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val permissionLocation = ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)
        val permissionRecordAudio = ContextCompat.checkSelfPermission(activity!!, Manifest.permission.RECORD_AUDIO)


        val listPermissionsNeeded = ArrayList<String>()

        if (camerapermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (permissionRecordAudio != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO)
        }
        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(activity!!, listPermissionsNeeded.toTypedArray(), REQUEST_ID_MULTIPLE_PERMISSIONS)
            return false
        }
        return true
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

    private fun toggleForm(isVisible: Boolean, isOilChange: Boolean = false) {
        if (isVisible) {
            view?.create_txv_where?.visibility = View.VISIBLE
            view?.create_spinner_location?.visibility = View.VISIBLE
            view?.create_etx_layout_mileage?.visibility = View.VISIBLE
            view?.create_etx_layout_body?.visibility = View.VISIBLE
            view?.create_btn_submit?.visibility = View.VISIBLE
            view?.create_etx_layout_price?.visibility = View.VISIBLE
            view?.create_linear_layout_oil?.visibility = if (isOilChange) View.VISIBLE else View.GONE

            if (!isScrolled) {
                Timer("scroll", false).schedule(500) {
                    activity?.runOnUiThread {
                        view?.create_scroll?.height?.let {
                            view?.create_scroll?.smoothScrollTo(0, it)
                        }
                    }
                }
                isScrolled = true
            }
        } else {
            view?.create_txv_where?.visibility = View.GONE
            view?.create_spinner_location?.visibility = View.GONE
            view?.create_etx_layout_mileage?.visibility = View.GONE
            view?.create_etx_layout_body?.visibility = View.GONE
            view?.create_btn_submit?.visibility = View.GONE
            view?.create_etx_layout_price?.visibility = View.GONE
            view?.create_linear_layout_oil?.visibility = View.GONE

            resetInputs()
            repairTypesAdapter.resetSelectedItem()
            isScrolled = false
        }
    }

    private fun resetInputs() {
        view?.create_etx_mileage?.text?.clear()
        view?.create_etx_price?.text?.clear()
        view?.create_etx_body?.text?.clear()
        view?.create_etx_oil?.text?.clear()
        view?.create_etx_oil_mileage?.text?.clear()
    }

    private fun showDialogOK(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(activity!!)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show()
    }

    private fun explain(msg: String) {
        val dialog = AlertDialog.Builder(activity!!)
        dialog.setMessage(msg)
                .setPositiveButton("Yes") { paramDialogInterface, paramInt ->
                    //  permissionsclass.requestPermission(type,code);
                    startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:com.example.parsaniahardik.kotlin_marshmallowpermission")))
                }
                .setNegativeButton("Cancel") { paramDialogInterface, paramInt ->
                    //TODO: Cancel }
                    dialog.show()
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        Log.d(TAG, "Permission callback called-------")
        when (requestCode) {
            REQUEST_ID_MULTIPLE_PERMISSIONS -> {

                val perms = HashMap<String, Int>()
                // Initialize the map with both permissions
                perms[Manifest.permission.CAMERA] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.ACCESS_FINE_LOCATION] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.RECORD_AUDIO] = PackageManager.PERMISSION_GRANTED
                // Fill with actual results from user
                if (grantResults.size > 0) {
                    for (i in permissions.indices)
                        perms[permissions[i]] = grantResults[i]
                    // Check for both permissions
                    if (perms[Manifest.permission.CAMERA] == PackageManager.PERMISSION_GRANTED
                            && perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
                            && perms[Manifest.permission.ACCESS_FINE_LOCATION] == PackageManager.PERMISSION_GRANTED
                            && perms[Manifest.permission.RECORD_AUDIO] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "sms & location services permission granted")
                        // process the normal flow

                        //else any one or both the permissions are not granted
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ")
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
                        //                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.CAMERA)
                                || ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)
                                || ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.RECORD_AUDIO)) {
                            showDialogOK("Service Permissions are required for this app",
                                    DialogInterface.OnClickListener { dialog, which ->
                                        when (which) {
                                            DialogInterface.BUTTON_POSITIVE -> checkAndRequestPermissions()
                                            DialogInterface.BUTTON_NEGATIVE -> {
                                                //TODO: Negative button
                                                // proceed with logic by disabling the related features or quit the app.
                                            }

                                        }
                                    })
                        } else {
                            explain("You need to give some mandatory permissions to continue. Do you want to go to app settings?")
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }//permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                    }
                }
            }
        }
    }


    /**
     * OnRepairTypeInteraction implementation
     */
    private fun onRepairTypeSelected(type: RepairType) {
        val itemPos = repairTypesAdapter.getItemPosition(type)
        if (itemPos != -1)
            view?.create_rcv_list_types?.smoothScrollToPosition(itemPos)
        toggleForm(isVisible = true, isOilChange = type == RepairType.OIL_CHANGE)
    }

    companion object {
        @JvmStatic
        fun newInstance() = CreateFragment()

        private const val REQUEST_ID_MULTIPLE_PERMISSIONS = 1
        private const val TAG = "UPLOAD_IMAGE"
    }
}
