package com.gkgio.borsch_cooker.meals.addmeal

import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.ext.applySchedulers
import com.gkgio.borsch_cooker.navigation.Screens
import com.gkgio.borsch_cooker.utils.SingleLiveEvent
import com.gkgio.domain.meals.MealsUseCase
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

class MealSelectTypeViewModel @Inject constructor(
    screensNavigator: BaseScreensNavigator,
    private val router: Router,
    private val mealsUseCase: MealsUseCase
) : BaseViewModel(screensNavigator) {

    val lunchIsAvailable = SingleLiveEvent<Boolean>()
    val isInitialError = SingleLiveEvent<Boolean>()

    init {
        onLoadMeals()
        lunchIsAvailable.value = false
    }

    fun addMealClick() {
        router.navigateTo(Screens.AddMealScreen())
    }

    fun addLunchClick() {
        router.navigateTo(Screens.AddLunchScreen())
    }

    private fun onLoadMeals() {
        mealsUseCase
            .loadMeals()
            .applySchedulers()
            .subscribe(
                { lunchIsAvailable.value = it.size > 1 },
                { throwable ->
                    Timber.e(throwable, "Error load meals")
                    processThrowable(throwable)
                    isInitialError.value = true
                }
            )
            .addDisposable()
    }
}