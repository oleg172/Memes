package com.olmi.android.memes.ui.memes.list

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import com.olmi.android.memes.data.DEFAULT_STRING_VALUE
import com.olmi.android.memes.data.USER_TOKEN
import com.olmi.android.memes.data.interactor.MemosInteractorProvider
import com.olmi.android.memes.data.repo.MemesListResult
import com.olmi.android.memes.utils.SharedPreferencesUtils

class MemesListPresenter : ViewModel() {

    private val TAG = this.javaClass.name
    private val memesInteractor = MemosInteractorProvider.MEMES_INTERACTOR
    private lateinit var view: MemesListView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var memes: MutableLiveData<MemesListResult>
    private lateinit var viewLifecycle: LifecycleOwner

    fun onViewCreated(
        view: MemesListView,
        sharedPreferences: SharedPreferences,
        viewLifecycle: LifecycleOwner
    ) {
        this.view = view
        this.sharedPreferences = sharedPreferences
        this.viewLifecycle = viewLifecycle
    }

    fun getMemes(): LiveData<MemesListResult> {
        if (!this::memes.isInitialized) {
            memes = MutableLiveData()
            memes = memesInteractor.getMemes(getUserToken())
        }
        return memes
    }

    fun updateMemes() {
        memesInteractor.getMemes(getUserToken()).observe(viewLifecycle, Observer {
            memes.value = it
            view.isRefreshingShow(false)
        })
    }

    private fun getUserToken(): String {
        val token = SharedPreferencesUtils.retriveData(sharedPreferences, USER_TOKEN)
        if (token == DEFAULT_STRING_VALUE) {
            Log.e(TAG, "Can't get user token. Please authorize.")
            //TODO call view method to redirect user to Login activity
        }
        return token
    }
}
