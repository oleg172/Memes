package com.olmi.android.memes.data.response

data class Response<T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR
    }

    companion object {

        fun <T> success(data: T): Response<T> {
            return Response(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Response<T> {
            return Response(Status.ERROR, data, message)
        }
    }
}
