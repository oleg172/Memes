package com.olmi.android.memes.data.handler

import com.olmi.android.memes.data.exceptions.HttpCallFailureException
import com.olmi.android.memes.data.exceptions.NoNetworkException
import com.olmi.android.memes.data.exceptions.ServerUnreachableException
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import java.lang.reflect.Type
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class ErrorHandlingCallAdapterFactory private constructor() : CallAdapter.Factory() {
    private val original: RxJava3CallAdapterFactory = RxJava3CallAdapterFactory.create()

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *> {
        return RxCallAdapterWrapper(
            original.get(returnType, annotations, retrofit) as CallAdapter<out Any, *>
        )
    }

    private class RxCallAdapterWrapper<R> internal constructor(
        private val wrapped: CallAdapter<R, *>
    ) : CallAdapter<R, Any> {

        override fun responseType(): Type {
            return wrapped.responseType()
        }

        override fun adapt(call: Call<R>): Any {
            val adaptedCall = wrapped.adapt(call)

            if (adaptedCall is Observable<*>) {
                return adaptedCall.onErrorResumeNext { throwable: Throwable ->
                    when (throwable) {
                        is SocketTimeoutException -> Observable.error(NoNetworkException(throwable))
                        is UnknownHostException -> Observable.error(ServerUnreachableException(throwable))
                        is HttpException -> Observable.error(HttpCallFailureException(throwable))
                        else -> Observable.error(throwable)
                    }
                }
            }
            throw RuntimeException("Type not supported")
        }
    }

    companion object {
        fun create(): CallAdapter.Factory {
            return ErrorHandlingCallAdapterFactory()
        }
    }
}