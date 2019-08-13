package com.cheersapps.carhistory.feature.register

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_basic_info.view.*
import java.util.regex.Pattern


class BasicInfoFragment : BaseFragment() {

    private var listener: OnRegisterInteractionListener? = null


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_basic_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTextWatcher(view)
        initClicks(view)
    }

    private fun initTextWatcher(view: View) {
        view.register_etx_username.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val pattern = Pattern.compile("[^\\s]+")
                val matcher = pattern.matcher(s.toString())
                if (!matcher.matches()) {
                    view.register_txv_error_username.text = getString(R.string.no_spaces)
                    view.register_txv_error_username.visibility = View.VISIBLE
                } else {
                    view.register_txv_error_username.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    private fun initClicks(view: View) {
        view.register_txv_have_account.setOnClickListener {
            listener?.finishActivity()
        }

        view.register_btn_proceed.setOnClickListener {
            val fName = view.register_etx_fname.text
            val lName = view.register_etx_lname.text
            val username = view.register_etx_username.text
            var isErrors = Triple(first = false, second = false, third = false)

            if (fName.isNullOrEmpty()) {
                isErrors = isErrors.copy(first = true)
                view.register_txv_error_fname.text = getString(R.string.empty_field)
                view.register_txv_error_fname.visibility = View.VISIBLE
                view.register_etx_fname.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
            } else {
                view.register_txv_error_fname.visibility = View.GONE
            }

            if (lName.isNullOrEmpty()) {
                isErrors = isErrors.copy(second = true)
                view.register_txv_error_lname.text = getString(R.string.empty_field)
                view.register_txv_error_lname.visibility = View.VISIBLE
                view.register_etx_lname.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
            } else {
                view.register_txv_error_lname.visibility = View.GONE
            }

            if (username.isNullOrEmpty()) {
                isErrors = isErrors.copy(third = true)
                view.register_txv_error_username.text = getString(R.string.empty_field)
                view.register_txv_error_username.visibility = View.VISIBLE
                view.register_etx_username.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
            } else {
                val pattern = Pattern.compile("[^\\s]+")
                val matcher = pattern.matcher(username.toString())
                if(!matcher.matches()) {
                    isErrors = isErrors.copy(third = true)
                    view.register_txv_error_username.text = getString(R.string.no_spaces)
                    view.register_txv_error_username.visibility = View.VISIBLE
                    view.register_etx_username.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
                }else {
                    view.register_txv_error_username.visibility = View.GONE
                }
            }

            if (isErrors.first || isErrors.second || isErrors.third) return@setOnClickListener

            listener?.showPasswordFragment(fName.toString(), lName.toString(), username.toString())
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
        fun newInstance() = BasicInfoFragment()
    }
}
