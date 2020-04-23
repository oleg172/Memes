package com.olmi.android.memes.data.repo

import com.olmi.android.memes.data.request.UserRequest
import com.olmi.android.memes.data.response.LoginResponse
import io.reactivex.rxjava3.core.Observable

interface MemesRepo {

    fun login(userRequest: UserRequest): Observable<LoginResponse>
}