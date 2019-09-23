package com.cheersapps.carhistory.data.entity

import com.cheersapps.carhistory.R


enum class RepairType(val title: Int, val icon: Int) {
    OIL_CHANGE(R.string.oil_change, R.drawable.ic_oil),
    MAINTENANCE(R.string.maintenance, R.drawable.ic_cogs),
    PART_REF(R.string.part_ref, R.drawable.ic_car_part)
}
