package com.cheersapps.carhistory.data.local.converter

import androidx.room.TypeConverter
import com.cheersapps.carhistory.data.entity.Credentials
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.IOException

open class Converter {

    @TypeConverter
    fun toString(credentials: Credentials): String? {
        return try {
            ObjectMapper().writeValueAsString(credentials)
        } catch (e: IOException) {
            null
        }
    }

    @TypeConverter
    fun toCredentials(source: String): Credentials? {
        return try {
            ObjectMapper().readValue<Credentials>(source, Credentials::class.java)
        } catch (e: IOException) {
            null
        }
    }
}