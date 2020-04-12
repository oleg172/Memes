package com.olmi.android.memes.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.olmi.android.memes.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginView {

    companion object {
        const val SHOW_PASS = "showPass"
    }

    private lateinit var presenter: LoginPresenter
    private var showPass: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState != null) {
            showPass = savedInstanceState.getBoolean(SHOW_PASS, false)
        }
        presenter = ViewModelProvider(this).get(LoginPresenter::class.java)
        presenter.onViewCreated(this)

        initFields()
        initListeners()
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
            login_button.text = ""
            login_progress_bar.visibility = View.VISIBLE
        } else {
            login_button.text = resources.getString(R.string.login_button)
            login_progress_bar.visibility = View.GONE
        }
    }

    private fun initListeners() {
        login_field_value.addTextChangedListener(onLoginChangeListener())
        password_field_value.addTextChangedListener(onPasswordChangeListener())
        password_field.endIconImageButton.setOnClickListener(onIconClickListener())
        login_button.setOnClickListener(onButtonClickListener())
    }

    private fun initFields() {
        showPassword(showPass)
    }

    private fun onLoginChangeListener() = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            showLoginFieldEmptyError(false)
        }
    }


    private fun onPasswordChangeListener() = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val text = s ?: return
            showPasswordFieldEmptyError(false)
            presenter.checkPasswordField(
                text.toString(),
                resources.getInteger(R.integer.password_length)
            )
        }
    }

    private fun onIconClickListener() = object : View.OnClickListener {

        override fun onClick(view: View?) {
            showPass = !showPass
            val position = password_field_value.selectionEnd
            showPassword(showPass)
            password_field_value.setSelection(position)
        }
    }

    private fun onButtonClickListener() =
        View.OnClickListener { presenter.login(resources.getInteger(R.integer.password_length)) }

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
}
