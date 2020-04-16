package com.olmi.android.memes.ui.login

import com.olmi.android.memes.data.models.User

interface LoginView {

    fun getLoginInput(): String?

    fun getPasswordInput(): String?

    fun showLoginFieldEmptyError(show: Boolean)

    fun showPasswordFieldEmptyError(show: Boolean)

    fun showPasswordHelper(isHelperVisible: Boolean)

    fun buttonShowProgressBar(show: Boolean)

    fun displayError(error: Throwable)

    fun saveUserInformation(user: User)
}
