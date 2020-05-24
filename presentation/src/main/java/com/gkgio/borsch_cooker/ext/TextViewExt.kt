package com.gkgio.borsch_cooker.ext

import android.widget.TextView
import androidx.core.view.isVisible

fun TextView.setTextOrHide(text: CharSequence?) {
    isVisible = text.notIsNullOrBlank()
    setText(text)
}