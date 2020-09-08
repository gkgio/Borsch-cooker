package com.gkgio.borsch_cooker.meals.addmeal

import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.navigation.Screens
import com.gkgio.borsch_cooker.utils.SingleLiveEvent
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MealSelectTypeViewModel @Inject constructor(
    screensNavigator: BaseScreensNavigator,
    private val router: Router
) : BaseViewModel(screensNavigator) {

    val lunchIsAvailable = SingleLiveEvent<Boolean>()

    init {
        //TODO
        lunchIsAvailable.value = false
    }

    fun addMealClick() {
        router.navigateTo(Screens.AddMealScreen())
    }

    fun addLunchClick() {
        //TODO
        //router.navigateTo(Screens.AddMealScreen())
    }
}