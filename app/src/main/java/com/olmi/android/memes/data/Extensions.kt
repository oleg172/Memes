package com.olmi.android.memes.data

import com.olmi.android.memes.data.exceptions.HttpCallFailureException
import com.olmi.android.memes.data.exceptions.NoNetworkException
import com.olmi.android.memes.data.exceptions.ServerUnreachableException
import io.reactivex.rxjava3.core.Observable
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <T> Observable<T>.mapNetworkErrors(): Observable<T> =
    this.onErrorResumeNext { error ->
        when (error) {
            is SocketTimeoutException -> Observable.error(NoNetworkException(error))
            is UnknownHostException -> Observable.error(ServerUnreachableException(error))
            is HttpException -> Observable.error(HttpCallFailureException(error))
            else -> Observable.error(error)
        }
    }
