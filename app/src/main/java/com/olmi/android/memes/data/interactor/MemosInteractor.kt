package com.olmi.android.memes.data.interactor

import com.olmi.android.memes.data.request.UserRequest
import com.olmi.android.memes.data.repo.MemesRepo
import com.olmi.android.memes.data.response.LoginResponse
import io.reactivex.rxjava3.core.Observable

class MemosInteractor(
    private val repo: MemesRepo
) {

    fun login(userRequest: UserRequest): Observable<LoginResponse> = repo.login(userRequest)
}
