package com.gkgio.borsch_cooker.view

import android.text.Editable
import android.text.TextWatcher

open class AbstractTextWatcher : TextWatcher {
    override fun afterTextChanged(text: Editable?) {
        // stub
    }

    override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
        // stub
    }

    override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
        // stub
    }
}