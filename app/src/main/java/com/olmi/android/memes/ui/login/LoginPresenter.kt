package com.olmi.android.memes.ui.login

import androidx.lifecycle.ViewModel
import com.olmi.android.memes.utils.FieldValidationUtils

class LoginPresenter : ViewModel() {

    private var view: LoginView? = null

    fun onViewCreated(view: LoginView) {
        this.view = view
    }

    fun onViewDestroyed() {
        view = null
    }

    fun checkPasswordField(password: String?, passwordLength: Int) {
        if (!FieldValidationUtils.isPasswordValid(password, passwordLength)) {
            view?.showPasswordHelper(true)
        } else {
            view?.showPasswordHelper(false)
        }
    }

    fun login(passwordLength: Int) {
        view?.buttonShowProgressBar(true)
        if (!checkCredentials(view?.getLoginInput(), view?.getPasswordInput(), passwordLength)) {
            view?.buttonShowProgressBar(false)
            return
        }
    }

    private fun checkCredentials(login: String?, password: String?, passwordLength: Int): Boolean {
        var isCredentialsValid = true
        if (!FieldValidationUtils.isFieldEmpty(login)) {
            view?.showLoginFieldEmptyError(true)
            isCredentialsValid = false
        }
        if (!FieldValidationUtils.isFieldEmpty(password)) {
            view?.showPasswordFieldEmptyError(true)
            isCredentialsValid = false
        }
        if (FieldValidationUtils.isFieldEmpty(password) &&
            !FieldValidationUtils.isPasswordValid(password, passwordLength)) {
            view?.showPasswordHelper(true)
            isCredentialsValid = false
        }
        return isCredentialsValid
    }
}
