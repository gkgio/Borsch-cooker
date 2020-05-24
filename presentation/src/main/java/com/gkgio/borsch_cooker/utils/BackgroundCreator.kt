package com.gkgio.borsch_cooker.utils

import android.graphics.drawable.Drawable
import top.defaults.drawabletoolbox.DrawableBuilder

interface BackgroundCreator {
    fun create(): Drawable
}

interface BackgroundComponentCreator {
    fun create(): DrawableBuilder
}