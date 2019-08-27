package com.cheersapps.carhistory.core.activity

import android.content.Context
import android.content.DialogInterface
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
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
            title: String,
            message: String,
            listener: DialogInterface.OnClickListener? = null,
            withCloseButton: Boolean = false
    ) {
        val alertDialog = AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, listener)
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)


        if (withCloseButton) {
            alertDialog.setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss() }
        }
        alertDialog.show()
    }
}