package com.cheersapps.carhistory.data.entity

class Credentials() {

    constructor(username: String, password: String) : this() {
        this.username = username
        this.password = password
    }

    var username: String? = null
    var password: String? = null

    override fun toString(): String {
        return "Credentials(username=$username, password=$password)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Credentials

        if (username != other.username) return false
        if (password != other.password) return false

        return true
    }

    override fun hashCode(): Int {
        var result = username?.hashCode() ?: 0
        result = 31 * result + (password?.hashCode() ?: 0)
        return result
    }


}