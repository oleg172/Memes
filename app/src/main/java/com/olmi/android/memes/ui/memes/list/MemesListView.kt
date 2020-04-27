package com.olmi.android.memes.ui.memes.list

interface MemesListView {

    fun showProgressBar(show: Boolean)

    fun showLoadMemesError(error: Throwable?)

    fun isRefreshingShow(show: Boolean)
}
