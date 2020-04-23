package com.olmi.android.memes.ui.helper

import android.text.Editable
import android.text.TextWatcher

/*
* Custom class extends TextWatcher. This class can be used to track text changes in the input field.
* To track text changes need to pass function 'onChanged: (CharSequence?)' in which the user must
*  define the rules for processing text changes
* */
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
