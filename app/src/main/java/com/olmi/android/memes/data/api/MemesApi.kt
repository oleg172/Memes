package com.olmi.android.memes.data.api

import com.olmi.android.memes.data.request.UserRequest
import com.olmi.android.memes.data.response.LoginResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface MemesApi {

    @POST("auth/login")
    fun login(@Body userRequest: UserRequest): Observable<LoginResponse>
}
