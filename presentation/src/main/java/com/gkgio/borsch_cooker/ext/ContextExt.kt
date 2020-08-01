package com.gkgio.borsch_cooker.ext

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.os.Build.VERSION_CODES
import android.os.Build.VERSION
import androidx.annotation.PluralsRes
import java.util.*

fun Context.getColorCompat(@ColorRes colorId: Int) = ContextCompat.getColor(this, colorId)

fun Context.getDrawableCompat(@DrawableRes drawableId: Int): Drawable =
    AppCompatResources.getDrawable(this, drawableId)!!

fun Context.dpToPx(dp: Int): Int {
    return dp * (resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

fun Context.getColoredDrawableCompat(@DrawableRes drawableRes: Int, @ColorInt color: Int): Drawable? {
    return this.getDrawableCompat(drawableRes).apply {
        if (VERSION.SDK_INT >= VERSION_CODES.Q) {
            colorFilter = BlendModeColorFilter(color, BlendMode.SRC_IN)
        } else {
            @Suppress("DEPRECATION")
            setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }
    }
}

fun Context.getQuantityText(@PluralsRes id: Int, quantity: Int) =
    getResourcesByLocaleRu().getQuantityString(
        id,
        quantity,
        quantity
    )

private fun Context.getResourcesByLocaleRu(): Resources {
    val configuration = Configuration(resources.configuration).apply {
        setLocale(Locale("ru"))
    }

    return createConfigurationContext(configuration).resources
}