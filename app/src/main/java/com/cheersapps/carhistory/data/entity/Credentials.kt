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


}