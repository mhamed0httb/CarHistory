package com.cheersapps.carhistory.data.entity

import com.cheersapps.carhistory.R


enum class RepairType(val title: String, val icon: Int) {
    OIL_CHANGE("Oil Change", R.drawable.ic_cogs), MAINTENANCE("Maintenance", R.drawable.ic_oil)
}