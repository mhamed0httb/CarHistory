package com.cheersapps.carhistory.feature.register

interface OnRegisterInteractionListener {

    fun finishActivity()
    fun showPasswordFragment(fName: String, lName: String, username: String)
    fun back()
    fun completeRegistration(password: String)
}