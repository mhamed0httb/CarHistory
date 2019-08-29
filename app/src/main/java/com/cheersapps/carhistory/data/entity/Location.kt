package com.cheersapps.carhistory.data.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Location() {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    var id: String? = generateId()

    var name: String? = null

    private fun generateId(): String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz"
        return (1..5)
                .map { allowedChars.random() }
                .joinToString("")
    }

    constructor(name: String) : this() {
        this.name = name
    }

    override fun toString(): String {
        var string: String = ""
        this.name?.let { string = it.capitalize() }
        return string
    }
}