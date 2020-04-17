package com.olmi.android.memes.ui.helper

import android.text.Editable
import android.text.TextWatcher

class TextChangedHelper(
    val onChanged: (CharSequence?) -> Unit
) : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onChanged(s)
    }
}
