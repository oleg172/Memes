package com.olmi.android.memes.data.response.base

interface BaseResponse<T> {

    fun convert(): T
}
