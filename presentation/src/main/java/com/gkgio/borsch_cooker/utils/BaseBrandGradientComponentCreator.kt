package com.gkgio.borsch_cooker.utils

import android.content.Context
import com.gkgio.borsch_cooker.ext.getColorCompat
import top.defaults.drawabletoolbox.DrawableBuilder

class BaseBrandGradientComponentCreator(
    private val context: Context,
    private val colorStart: Int,
    private val colorEnd: Int
) : BackgroundComponentCreator {

    override fun create(): DrawableBuilder {
        return DrawableBuilder()
            .rectangle()
            .gradient()
            .gradientColors(
                context.getColorCompat(colorStart),
                context.getColorCompat(colorEnd),
                null
            )
            .linearGradient()
    }
}