package com.cheersapps.carhistory.feature.register

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.common.resource.ResourceState
import com.cheersapps.carhistory.core.activity.BaseActivity
import com.cheersapps.carhistory.core.activity.BaseActivityExtension.showMessage
import com.cheersapps.carhistory.core.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_password.view.*


class PasswordFragment : BaseFragment() {

    private var listener: OnRegisterInteractionListener? = null
    private val registerViewModel: RegisterViewModel by lazy {
        ViewModelProviders.of(activity!!)[RegisterViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_password, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClicks(view)
        initObservers()
    }

    private fun initObservers() {
        registerViewModel.observeInsertUser().observe(this, Observer { nullableResource ->
            nullableResource?.let { resource ->
                when (resource.status) {
                    ResourceState.LOADING -> {
                        showLoader()
                    }
                    ResourceState.ERROR -> {
                        hideLoader()
                        (activity as BaseActivity).showMessage(
                            context!!,
                            getString(R.string.error),
                            getString(R.string.error_register)
                        )
                    }
                    ResourceState.SUCCESS -> {
                        hideLoader()
                        val dialogListener = DialogInterface.OnClickListener { dialog, _ ->
                            listener?.finishActivity()
                            dialog.dismiss()
                        }
                        (activity as BaseActivity).showMessage(
                            context!!,
                            getString(R.string.success),
                            getString(R.string.success_register),
                            dialogListener
                        )
                    }
                }
            }
        })
    }

    private fun initClicks(view: View) {
        view.register_btn_cancel.setOnClickListener {
            listener?.back()
        }

        view.register_btn_submit.setOnClickListener {
            val password = view.register_etx_password.text
            val confirmPassword = view.login_etx_confirm_password.text
            var isErrors = Pair(first = false, second = false)

            if (password.isNullOrEmpty()) {
                isErrors = isErrors.copy(first = true)
                view.login_txv_error_password.text = getString(R.string.empty_field)
                view.login_txv_error_password.visibility = View.VISIBLE
                view.register_etx_password.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
            } else {
                view.login_txv_error_password.visibility = View.GONE
            }

            if (confirmPassword.isNullOrEmpty()) {
                isErrors = isErrors.copy(second = true)
                view.login_txv_error_confirm_password.text = getString(R.string.empty_field)
                view.login_txv_error_confirm_password.visibility = View.VISIBLE
                view.login_etx_confirm_password.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
            } else {
                view.login_txv_error_confirm_password.visibility = View.GONE
            }

            if (isErrors.first || isErrors.second) return@setOnClickListener

            if (password.toString() != confirmPassword.toString()) {
                view.login_txv_error_password.text = getString(R.string.no_match)
                view.login_txv_error_password.visibility = View.VISIBLE
                view.register_etx_password.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
                view.login_etx_confirm_password.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
                return@setOnClickListener
            } else {
                view.login_txv_error_password.visibility = View.GONE
            }

            listener?.completeRegistration(password.toString())
        }
    }

    private fun showLoader() {
        view?.register_loader?.show()
        view?.register_btn_submit?.text = null
        view?.register_btn_submit?.isClickable = false
        view?.register_btn_cancel?.isClickable = false
        view?.register_etx_password?.isEnabled = false
        view?.login_etx_confirm_password?.isEnabled = false
    }

    private fun hideLoader() {
        view?.register_loader?.hide()
        view?.register_btn_submit?.text = getString(R.string.sign_up)
        view?.register_btn_submit?.isClickable = true
        view?.register_btn_cancel?.isClickable = true
        view?.register_etx_password?.isEnabled = true
        view?.login_etx_confirm_password?.isEnabled = true
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnRegisterInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnRegisterInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    companion object {

        @JvmStatic
        fun newInstance() = PasswordFragment()
    }
}
