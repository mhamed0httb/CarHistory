package com.cheersapps.carhistory.feature.register

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_password.view.*


class PasswordFragment : BaseFragment() {

    private var listener: OnRegisterInteractionListener? = null


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_password, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClicks(view)
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
