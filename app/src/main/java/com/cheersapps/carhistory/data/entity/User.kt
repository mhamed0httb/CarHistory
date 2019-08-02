package com.cheersapps.carhistory.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import org.jetbrains.annotations.NonNls
import java.io.Serializable


@Entity
class User : Serializable {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    var id: String = generateId()

    var firstName: String? = null
    var lastName: String? = null
    var credentials: Credentials = Credentials()

    override fun toString(): String {
        return "User(id='$id', firstName=$firstName, lastName=$lastName, credentials=$credentials)"
    }


    private fun generateId(): String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz"
        return (1..5)
                .map { allowedChars.random() }
                .joinToString("")
    }


}