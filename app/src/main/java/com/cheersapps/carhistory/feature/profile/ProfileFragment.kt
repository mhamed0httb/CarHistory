package com.cheersapps.carhistory.feature.profile

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.fragment.BaseFragment
import com.cheersapps.carhistory.feature.home.HomeViewModel
import com.cheersapps.carhistory.usecase.profile.FieldsValidator
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.dialog_edit_password.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.util.regex.Pattern


class ProfileFragment : BaseFragment() {

    private var listener: OnProfileInteractionListener? = null
    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this)[HomeViewModel::class.java]
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initTextWatcher(view)
        initClicks(view)
        initObservers(view)
    }

    private fun initObservers(view: View) {
        homeViewModel.getLoggedInUser().observe(this, Observer {loggedInUser->
            loggedInUser?.let {
                val builder = StringBuilder()
                builder.append(it.firstName)
                builder.append(" ")
                builder.append(it.lastName)
                view.profile_txv_logged_user_name.text = builder.toString()

                view.profile_etx_fname.setText(it.firstName)
                view.profile_etx_lname.setText(it.lastName)
                view.profile_etx_username.setText(it.credentials.username)
            }
        })
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
                if (!matcher.matches()) {
                    isErrors = isErrors.copy(third = true)
                    view.profile_txv_error_username.text = getString(R.string.no_spaces)
                    view.profile_txv_error_username.visibility = View.VISIBLE
                    view.profile_etx_username.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
                } else {
                    view.profile_txv_error_username.visibility = View.GONE
                }
            }

            if (isErrors.first || isErrors.second || isErrors.third) return@setOnClickListener

            // TODO: Update User info now
            view.profile_btn_submit.visibility = View.GONE
            view.profile_btn_submit.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
        }

        view.profile_btn_edit_password.setOnClickListener {
            val dialogView: View = layoutInflater.inflate(R.layout.dialog_edit_password, null)
            val dialog = BottomSheetDialog(context!!)

            val editPasswordView: View = prepareEditPasswordView(dialogView)

            dialog.setContentView(editPasswordView)
            dialog.show()

        }
    }

    private fun prepareEditPasswordView(dialogView: View): View {
        dialogView.edit_password_btn_submit.setOnClickListener {
            val oldPass = dialogView.edit_password_etx_old_password.text.toString()
            val newPass = dialogView.edit_password_etx_password.text.toString()
            val confirmPass = dialogView.edit_password_etx_confirm_password.text.toString()

            var errors = Triple(first = false, second = false, third = false)
            val resultFieldsValidator = homeViewModel.checkEditPasswordFields(oldPass, newPass, confirmPass)

            when (resultFieldsValidator.first) {
                FieldsValidator.EMPTY -> {
                    dialogView.edit_password_txv_error_old_password.text = getString(R.string.empty_field)
                    dialogView.edit_password_txv_error_old_password.visibility = View.VISIBLE
                    dialogView.edit_password_etx_old_password.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
                    errors = errors.copy(first = false)

                }
                FieldsValidator.NOT_EMPTY -> {
                    dialogView.edit_password_txv_error_old_password.visibility = View.GONE
                    errors = errors.copy(first = true)
                }
                FieldsValidator.NO_MATCH -> {
                }
                FieldsValidator.MATCH -> {
                }
            }

            when (resultFieldsValidator.second) {
                FieldsValidator.EMPTY -> {
                    dialogView.edit_password_txv_error_password.text = getString(R.string.empty_field)
                    dialogView.edit_password_txv_error_password.visibility = View.VISIBLE
                    dialogView.edit_password_etx_password.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
                    errors = errors.copy(second = false)
                }
                FieldsValidator.NOT_EMPTY -> {
                    dialogView.edit_password_txv_error_password.visibility = View.GONE
                    errors = errors.copy(second = true)
                }
                FieldsValidator.NO_MATCH -> {
                    dialogView.edit_password_txv_error_password.text = getString(R.string.no_match)
                    dialogView.edit_password_txv_error_password.visibility = View.VISIBLE
                    dialogView.edit_password_etx_password.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
                    errors = errors.copy(second = false)
                }
                FieldsValidator.MATCH -> {
                    dialogView.edit_password_txv_error_password.visibility = View.GONE
                    errors = errors.copy(second = true)
                }
            }

            when (resultFieldsValidator.third) {
                FieldsValidator.EMPTY -> {
                    dialogView.edit_password_txv_error_confirm_password.text = getString(R.string.empty_field)
                    dialogView.edit_password_txv_error_confirm_password.visibility = View.VISIBLE
                    dialogView.edit_password_etx_confirm_password.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
                    errors = errors.copy(third = false)
                }
                FieldsValidator.NOT_EMPTY -> {
                    dialogView.edit_password_txv_error_confirm_password.visibility = View.GONE
                    errors = errors.copy(third = true)
                }
                FieldsValidator.NO_MATCH -> {
                }
                FieldsValidator.MATCH -> {
                }
            }

            if(errors.first && errors.second && errors.third){
                dialogView.edit_password_btn_submit.text = ""
                dialogView.edit_password_loader.visibility = View.VISIBLE
            }
        }

        return dialogView
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
        fun newInstance() = ProfileFragment()
    }
}
