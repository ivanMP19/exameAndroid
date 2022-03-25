package com.ivan.marin.exameandroid.data.remote

import android.util.Log
import com.ivan.marin.exameandroid.utils.NetworkState
import retrofit2.Response

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): NetworkState<T> {
        try {

            val response = call()
            Log.d("TAG", response.toString())
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return NetworkState.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): NetworkState<T> {
        Log.d("TAG",message)
        return NetworkState.error("Network call has failed for a following reason: $message")
    }
}