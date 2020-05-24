package com.gkgio.borsch_cooker.main

import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.utils.SingleLiveEvent
import javax.inject.Inject

class MainViewModel @Inject constructor(
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator){

    val openFirstTab = SingleLiveEvent<Unit>()

    init {
        openFirstTab.call()
    }
}