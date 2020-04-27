package com.olmi.android.memes.data.interactor

import androidx.lifecycle.MutableLiveData
import com.olmi.android.memes.data.repo.MemesListResult
import com.olmi.android.memes.data.repo.MemesRepo
import com.olmi.android.memes.data.request.UserRequest
import com.olmi.android.memes.data.response.LoginResponse
import io.reactivex.rxjava3.core.Observable

class MemesInteractor(
    private val repo: MemesRepo
) {

    fun login(userRequest: UserRequest): Observable<LoginResponse> = repo.login(userRequest)

    fun getMemes(token: String): MutableLiveData<MemesListResult> = repo.getMemes(token)
}
