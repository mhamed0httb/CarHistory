package com.cheersapps.carhistory.feature.home

import android.view.View
import com.cheersapps.carhistory.data.entity.Repair

interface OnHomeInteractionListener {
    fun detailsRepair(repair: Repair, sharedView: View)
    fun navigateTo(fragmentName: String)
    fun logout()
    fun manageLocations()
}