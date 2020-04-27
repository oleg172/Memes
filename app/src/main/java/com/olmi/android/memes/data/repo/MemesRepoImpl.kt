package com.olmi.android.memes.data.repo

import androidx.lifecycle.MutableLiveData
import com.olmi.android.memes.data.request.UserRequest
import com.olmi.android.memes.data.response.LoginResponse
import com.olmi.android.memes.data.service.NetworkService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class MemesRepoImpl : MemesRepo {

    override fun login(userRequest: UserRequest): Observable<LoginResponse> {
        return NetworkService.getApi().login(userRequest)
    }

    override fun getMemes(token: String): MutableLiveData<MemesListResult> {
        val memes = MutableLiveData<MemesListResult>()
        NetworkService.getApi().getMemes(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { data ->
                    memes.value = Result.success(data)
                },
                { error ->
                    memes.value = Result.failure(error)
                }
            )
        return memes
    }
}
