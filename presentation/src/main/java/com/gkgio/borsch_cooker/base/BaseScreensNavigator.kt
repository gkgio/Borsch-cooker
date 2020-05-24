package com.gkgio.borsch_cooker.base

interface BaseScreensNavigator {
    fun navigateBack()
    fun navigateToMarket(packageName: String)
    fun navigateBackTo()
}