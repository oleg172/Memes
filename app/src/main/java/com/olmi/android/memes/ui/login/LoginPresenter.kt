package com.olmi.android.memes.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.olmi.android.memes.data.interactor.MemosInteractorProvider
import com.olmi.android.memes.data.request.UserRequest
import com.olmi.android.memes.utils.FieldValidationUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginPresenter : ViewModel() {

    private val TAG = this.javaClass.name
    private val memosInteractor = MemosInteractorProvider.memosInteractor
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
        val user = UserRequest(
            view?.getLoginInput() ?: "", view?.getPasswordInput() ?: ""
        )
        memosInteractor.login(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.i(TAG, "Successfully login. User ifromation: ${it.convert()}")
                    view?.buttonShowProgressBar(false)
                    view?.saveUserInformation(it.convert())
                },
                { error ->
                    Log.e(TAG, "Can't login because of error: ${error.message}")
                    view?.displayError(error)
                    view?.buttonShowProgressBar(false)
                }
            )
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
            !FieldValidationUtils.isPasswordValid(password, passwordLength)
        ) {
            view?.showPasswordHelper(true)
            isCredentialsValid = false
        }
        return isCredentialsValid
    }
}
