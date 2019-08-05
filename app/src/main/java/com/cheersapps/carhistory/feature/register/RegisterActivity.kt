package com.cheersapps.carhistory.feature.register

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.cheersapps.carhistory.R
import com.cheersapps.carhistory.core.activity.BaseActivity
import com.cheersapps.carhistory.core.activity.BaseActivityExtension.replaceFragmentSafely

class RegisterActivity : BaseActivity(), OnRegisterInteractionListener {


    private val registerViewModel: RegisterViewModel by lazy {
        ViewModelProviders.of(this)[RegisterViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        replaceFragmentSafely(
            R.id.register_container,
            BasicInfoFragment.newInstance(),
            BasicInfoFragment::class.java.simpleName,
            false
        )
    }

    /**
     * OnRegisterInteractionListener interface Implementations
     */
    override fun finishActivity() {
        finish()
    }

    override fun showPasswordFragment(fName: String, lName: String, username: String) {
        registerViewModel.userToRegister.firstName = fName
        registerViewModel.userToRegister.lastName = lName
        registerViewModel.userToRegister.credentials.username = username

        replaceFragmentSafely(
            R.id.register_container,
            PasswordFragment.newInstance(),
            PasswordFragment::class.java.simpleName,
            true
        )

    }

    override fun back() {
        onBackPressed()
    }

    override fun completeRegistration(password: String) {
        registerViewModel.userToRegister.credentials.password = password
        registerViewModel.storeUserToDatabase(registerViewModel.userToRegister)
    }

}
