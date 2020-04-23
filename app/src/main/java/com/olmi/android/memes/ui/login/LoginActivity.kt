package com.olmi.android.memes.ui.login

import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.olmi.android.memes.R
import com.olmi.android.memes.data.exceptions.HttpCallFailureException
import com.olmi.android.memes.data.exceptions.NoNetworkException
import com.olmi.android.memes.data.exceptions.ServerUnreachableException
import com.olmi.android.memes.ui.helper.TextChangedHelper
import com.olmi.android.memes.utils.SharedPreferencesUtils
import kotlinx.android.synthetic.main.activity_login.*

const val SHOW_PASS = "showPass"

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var presenter: LoginPresenter
    private var showPass: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState != null) {
            showPass = savedInstanceState.getBoolean(SHOW_PASS, false)
        }
        presenter = ViewModelProvider(this).get(LoginPresenter::class.java)
        presenter.onViewCreated(this, SharedPreferencesUtils.getPrefs(this))

        initFields()
        initFieldsListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(SHOW_PASS, showPass)
    }

    override fun getLoginInput() = login_field_value.text.toString()

    override fun getPasswordInput() = password_field_value.text.toString()

    override fun showLoginFieldEmptyError(show: Boolean) {
        if (show) {
            login_field.setError(resources.getString(R.string.error_field_empty), false)
        } else {
            login_field.removeError()
        }
    }

    override fun showPasswordFieldEmptyError(show: Boolean) {
        if (show) {
            password_field.setError(resources.getString(R.string.error_field_empty), false)
        } else {
            password_field.removeError()
        }
    }

    override fun showPasswordHelper(isHelperVisible: Boolean) {
        if (isHelperVisible && TextUtils.isEmpty(password_field.helperText)) {
            password_field.helperText = resources.getString(R.string.password_field_helper)
        } else if (!isHelperVisible) {
            password_field.helperText = null
        }
    }

    override fun buttonShowProgressBar(show: Boolean) {
        if (show) {
            login_btn.text = ""
            login_progress_bar.isVisible = true
        } else {
            login_btn.text = resources.getString(R.string.login_button)
            login_progress_bar.isVisible = false
        }
    }

    override fun displayError(error: Throwable) {
        when (error) {
            is NoNetworkException -> showSnackbar(resources.getString(R.string.no_internet_connection_error))
            is ServerUnreachableException -> showSnackbar(resources.getString(R.string.server_unreachable_error))
            is HttpCallFailureException -> showSnackbar(resources.getString(R.string.http_call_error))
            else -> showSnackbar(resources.getString(R.string.login_error))
        }
    }

    private fun initFieldsListeners() {
        login_field_value.addTextChangedListener(TextChangedHelper {
            onLoginFieldTextChanged()
        })
        password_field_value.addTextChangedListener(TextChangedHelper { text: CharSequence? ->
            onPasswordFieldTextChanged(text)
        })
        password_field.endIconImageButton.setOnClickListener(onIconClickListener())
        login_btn.setOnClickListener {
            presenter.login(resources.getInteger(R.integer.password_length))
        }
    }

    private fun initFields() {
        showPassword(showPass)
    }

    private fun onLoginFieldTextChanged() {
        showLoginFieldEmptyError(false)
    }


    private fun onPasswordFieldTextChanged(s: CharSequence?) {
        val text = s ?: return
        showPasswordFieldEmptyError(false)
        presenter.checkPasswordField(
            text.toString(),
            resources.getInteger(R.integer.password_length)
        )
    }

    private fun onIconClickListener() = View.OnClickListener {
        showPass = !showPass
        val position = password_field_value.selectionEnd
        showPassword(showPass)
        password_field_value.setSelection(position)
    }

    private fun showPassword(showPass: Boolean) {
        if (showPass) {
            password_field.setEndIcon(R.drawable.ic_eye_on)
            password_field_value.inputType = InputType.TYPE_CLASS_TEXT or
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            password_field.setEndIcon(R.drawable.ic_eye_off)
            password_field_value.inputType = InputType.TYPE_CLASS_TEXT or
                    InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
    }

    private fun showSnackbar(msg: String) {
        Snackbar.make(
            findViewById(R.id.login_view),
            msg,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}
