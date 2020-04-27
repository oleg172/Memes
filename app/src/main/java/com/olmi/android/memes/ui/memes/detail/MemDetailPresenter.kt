package com.olmi.android.memes.ui.memes.detail

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel

class MemDetailPresenter : ViewModel() {

    private lateinit var view: MemDetailView
    private lateinit var sharedPreferences: SharedPreferences

    fun onViewCreated(
        view: MemDetailView,
        sharedPreferences: SharedPreferences
    ) {
        this.view = view
        this.sharedPreferences = sharedPreferences
    }

}
