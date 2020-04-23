package com.olmi.android.memes.ui.login

interface LoginView {

    fun getLoginInput(): String

    fun getPasswordInput(): String

    fun showLoginFieldEmptyError(show: Boolean)

    fun showPasswordFieldEmptyError(show: Boolean)

    fun showPasswordHelper(isHelperVisible: Boolean)

    fun buttonShowProgressBar(show: Boolean)

    fun displayError(error: Throwable)

}
