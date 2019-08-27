package com.cheersapps.carhistory.data.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cheersapps.carhistory.utils.DateUtils
import java.io.Serializable

@Entity
class Repair: Serializable {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    var id: String = generateId()

    var body: String? = null
    var date: Long? = null

    val icon: Int?
        get() {
        type?.let {
            return RepairType.valueOf(it).icon
        }
        return null
    }


    var createdAt: Long = DateUtils.currentFullDateTimestamp()
    var type: String? = null
    var location: String? = null

    private fun generateId(): String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz"
        return (1..5)
                .map { allowedChars.random() }
                .joinToString("")
    }
}