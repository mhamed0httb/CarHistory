package com.cheersapps.carhistory.feature.profile

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
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.util.regex.Pattern

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileFragment : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnProfileInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTextWatcher(view)
        initClicks(view)
    }

    private fun initTextWatcher(view: View) {
        view.profile_etx_username.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val pattern = Pattern.compile("[^\\s]+")
                val matcher = pattern.matcher(s.toString())
                if (!matcher.matches()) {
                    view.profile_txv_error_username.text = getString(R.string.no_spaces)
                    view.profile_txv_error_username.visibility = View.VISIBLE
                } else {
                    view.profile_txv_error_username.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    private fun initClicks(view: View) {
        view.profile_fab_logout.setOnClickListener {
            listener?.logout()
        }

        view.profile_fab_edit.setOnClickListener {
            val slideUp = AnimationUtils.loadAnimation(context, R.anim.fade_in)
            view.profile_etx_fname.isEnabled = true
            view.profile_etx_lname.isEnabled = true
            view.profile_etx_username.isEnabled = true
            if (view.profile_btn_submit.visibility != View.VISIBLE) {
                view.profile_btn_submit.visibility = View.VISIBLE
                view.profile_btn_submit.startAnimation(slideUp)
            }
        }

        view.profile_btn_submit.setOnClickListener {
            val fName = view.profile_etx_fname.text
            val lName = view.profile_etx_lname.text
            val username = view.profile_etx_username.text
            var isErrors = Triple(first = false, second = false, third = false)

            if (fName.isNullOrEmpty()) {
                isErrors = isErrors.copy(first = true)
                view.profile_txv_error_fname.text = getString(R.string.empty_field)
                view.profile_txv_error_fname.visibility = View.VISIBLE
                view.profile_etx_fname.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
            } else {
                view.profile_txv_error_fname.visibility = View.GONE
            }

            if (lName.isNullOrEmpty()) {
                isErrors = isErrors.copy(second = true)
                view.profile_txv_error_lname.text = getString(R.string.empty_field)
                view.profile_txv_error_lname.visibility = View.VISIBLE
                view.profile_etx_lname.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
            } else {
                view.profile_txv_error_lname.visibility = View.GONE
            }


            if (username.isNullOrEmpty()) {
                isErrors = isErrors.copy(third = true)
                view.profile_txv_error_username.text = getString(R.string.empty_field)
                view.profile_txv_error_username.visibility = View.VISIBLE
                view.profile_etx_username.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
            } else {
                val pattern = Pattern.compile("[^\\s]+")
                val matcher = pattern.matcher(username.toString())
                if(!matcher.matches()) {
                    isErrors = isErrors.copy(third = true)
                    view.profile_txv_error_username.text = getString(R.string.no_spaces)
                    view.profile_txv_error_username.visibility = View.VISIBLE
                    view.profile_etx_username.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
                }else {
                    view.profile_txv_error_username.visibility = View.GONE
                }
            }

            if (isErrors.first || isErrors.second || isErrors.third) return@setOnClickListener

            // TODO: Update User info now
            view.profile_btn_submit.visibility = View.GONE
            view.profile_btn_submit.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnProfileInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnProfileInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnProfileInteractionListener {
        fun logout()
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ProfileFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
