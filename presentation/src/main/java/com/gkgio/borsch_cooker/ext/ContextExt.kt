package com.gkgio.borsch_cooker.ext

import android.content.Context
import android.content.Intent
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
import android.net.Uri
import android.os.Build.VERSION_CODES
import android.os.Build.VERSION
import androidx.annotation.PluralsRes
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsSession
import timber.log.Timber
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

fun Context.openLink(url: String, customTabsSession: CustomTabsSession? = null): Boolean {
    try {
        CustomTabsIntent.Builder(customTabsSession)
            .build()
            .launchUrl(this, Uri.parse(url))
        return true
    } catch (throwable: Throwable) {
        Timber.tag("Context::openLink").e(throwable, "CustomTabsIntent error on url: $url")
    }

    return openLinkInBrowser(url)
}


private fun Context.openLinkInBrowser(url: String): Boolean {
    val intent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
        addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
    }

    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
        return true
    }

    return false
}