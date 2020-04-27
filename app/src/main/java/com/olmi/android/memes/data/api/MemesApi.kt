package com.olmi.android.memes.data.api

import com.olmi.android.memes.data.models.Mem
import com.olmi.android.memes.data.request.UserRequest
import com.olmi.android.memes.data.response.LoginResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MemesApi {


    /**
     * login - method that allows the user to log in and get detailed information about the user
     * @param userRequest - body, which contains login and password information
     * @return - detail user information
     * */
    @POST("auth/login")
    fun login(@Body userRequest: UserRequest): Observable<LoginResponse>

    /**
     * getMemes - get all memese from server
     * @param auth - user token
     * @return - list of memes
     * */
    @GET("memes")
    fun getMemes(@Header("Authorization") auth: String): Observable<List<Mem>>
}
