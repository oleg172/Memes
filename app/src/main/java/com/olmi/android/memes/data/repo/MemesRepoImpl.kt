package com.olmi.android.memes.data.repo

import com.olmi.android.memes.data.mapNetworkErrors
import com.olmi.android.memes.data.request.UserRequest
import com.olmi.android.memes.data.response.LoginResponse
import com.olmi.android.memes.data.service.NetworkService
import io.reactivex.rxjava3.core.Observable


class MemesRepoImpl : MemesRepo {

    override fun login(userRequest: UserRequest): Observable<LoginResponse> {
        return NetworkService.getApi().login(userRequest)
            .mapNetworkErrors()
    }
}
