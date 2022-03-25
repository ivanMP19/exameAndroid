package com.ivan.marin.exameandroid.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map

import kotlinx.coroutines.Dispatchers

fun <T,A> performGetOperation(databaseQuery: () -> LiveData<T>,
                              networkCall: suspend () -> NetworkState<A>,
                              saveCallResult: suspend (A) -> Unit): LiveData<NetworkState<T>> =
    liveData(Dispatchers.IO){
        emit(NetworkState.loading())
        val source = databaseQuery.invoke().map { NetworkState.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == NetworkState.Status.SUCCESS){
            saveCallResult(responseStatus.data!!)
        }else if (responseStatus.status == NetworkState.Status.FAILED){
            emit(NetworkState.error(responseStatus.msg!!))
            emitSource(source)
        }
    }