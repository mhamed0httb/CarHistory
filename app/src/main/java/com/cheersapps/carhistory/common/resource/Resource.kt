package com.cheersapps.carhistory.common.resource

class Resource<out T> constructor(val status: ResourceState, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T): Resource<T> = Resource(ResourceState.SUCCESS, data, null)

        fun <T> error(message: String): Resource<T> = Resource(ResourceState.ERROR, null, message)

        fun <T> loading(): Resource<T> = Resource(ResourceState.LOADING, null, null)
    }


}