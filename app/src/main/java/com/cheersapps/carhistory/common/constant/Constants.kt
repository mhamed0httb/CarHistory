package com.cheersapps.carhistory.common.constant

import com.cheersapps.carhistory.feature.create.CreateFragment
import com.cheersapps.carhistory.feature.home.ListRepairsFragment
import com.cheersapps.carhistory.feature.profile.ProfileFragment
import com.cheersapps.carhistory.feature.statistics.StatisticsFragment

object Constants {

    const val STAY_LOGGED_IN = "stay_logged_in"
    const val LOGGED_IN_USER_ID = "logged_in_user_id"

    const val ARG_REPAIR = "arg_repair"

    const val APP_LANGUAGE = "app_language"

    val homeNavigation = arrayOf(
        ListRepairsFragment::class.java.simpleName,
        CreateFragment::class.java.simpleName,
        ProfileFragment::class.java.simpleName,
        StatisticsFragment::class.java.simpleName
    )
}