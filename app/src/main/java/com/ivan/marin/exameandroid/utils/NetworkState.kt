package com.ivan.marin.exameandroid.utils

data class NetworkState<out T> (val status: Status?, val data: T?, val msg: String?) {
    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED
    }

    companion object {
        fun <T> success(data: T): NetworkState<T> {
            return NetworkState(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): NetworkState<T> {
            return NetworkState(Status.FAILED, data, message)
        }

        fun <T> loading(data: T? = null): NetworkState<T> {
            return NetworkState(Status.RUNNING, data, null)
        }
    }
}