package com.cheersapps.carhistory.data.entity

import com.cheersapps.carhistory.utils.DateUtils

class Repair {

    var id: String? = null
    var body: String? = null
    var date: String? = null
    var icon: Int? = null
    var isSelected: Boolean = false

    var createdAt: Long = DateUtils.currentFullDateTimestamp()
}