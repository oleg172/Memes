package com.olmi.android.memes.data.repo

import androidx.lifecycle.MutableLiveData
import com.olmi.android.memes.data.models.Mem
import com.olmi.android.memes.data.request.UserRequest
import com.olmi.android.memes.data.response.LoginResponse
import io.reactivex.rxjava3.core.Observable

typealias  MemesListResult = Result<List<Mem>>

interface MemesRepo {

    fun login(userRequest: UserRequest): Observable<LoginResponse>

    fun getMemes(token: String): MutableLiveData<MemesListResult>
}