package com.cheersapps.carhistory.core.activity

import android.support.annotation.IdRes
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
}