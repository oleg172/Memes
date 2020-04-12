package com.olmi.android.memes.utils

import android.text.TextUtils

object FieldValidationUtils {

    fun isFieldEmpty(fieldValue: String?): Boolean {
        return fieldValue?.let {
            !TextUtils.isEmpty(fieldValue)
        } ?: false
    }

    fun isPhoneNumberValid(phoneNumber: String?): Boolean {
        return phoneNumber?.let {
            !TextUtils.isEmpty(phoneNumber)
                    && android.util.Patterns.PHONE.matcher(phoneNumber).matches()
        } ?: false
    }

    fun isPasswordValid(password: String?, passwordLength: Int): Boolean {
        return password?.let {
            !TextUtils.isEmpty(password) && password.length >= passwordLength
        } ?: false
    }
}
