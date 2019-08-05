package com.cheersapps.carhistory.core.activity

import android.content.Context
import android.content.DialogInterface
import android.support.annotation.IdRes
import android.support.v7.app.AlertDialog
import com.cheersapps.carhistory.core.fragment.BaseFragment

object BaseActivityExtension {

    fun BaseActivity.replaceFragmentSafely(
        @IdRes layout: Int,
        fragment: BaseFragment,
        tag: String,
        addToBackStack: Boolean,
        allowStateLoss: Boolean = false
    ) {

        if (tag.isNotEmpty()) {
            val manager = supportFragmentManager

            if (manager != null && manager.findFragmentByTag(tag) == null) {
                val transaction = manager.beginTransaction()
                transaction.replace(layout, fragment, tag)
                if (addToBackStack) transaction.addToBackStack(tag)
                if (!manager.isStateSaved) {
                    transaction.commit()
                } else if (allowStateLoss) {
                    transaction.commitAllowingStateLoss()
                }
                manager.executePendingTransactions()
            }
        }
    }

    fun BaseActivity.showMessage(
        context: Context,
        title: String,
        message: String,
        listener: DialogInterface.OnClickListener? = null
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.yes, listener)
            //.setIcon(android.R.drawable.ic_dialog_alert)
            .setCancelable(false)
            .show()

    }
}