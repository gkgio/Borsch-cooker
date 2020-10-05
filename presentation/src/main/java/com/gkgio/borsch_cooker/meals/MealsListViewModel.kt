package com.gkgio.borsch_cooker.meals

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.ext.applySchedulers
import com.gkgio.borsch_cooker.ext.isNonInitialized
import com.gkgio.borsch_cooker.ext.nonNullValue
import com.gkgio.borsch_cooker.utils.events.LunchesListChanged
import com.gkgio.borsch_cooker.utils.events.MealsListChanged
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.domain.meals.MealsUseCase
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

class MealsListViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    private val mealsUseCase: MealsUseCase,
    private val mealsItemUiTransformer: MealsItemUiTransformer,
    private val lunchesItemUiTransformer: LunchesItemUiTransformer,
    private val lunchesListChanged: LunchesListChanged,
    private val mealsListChanged: MealsListChanged,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()

    fun init(mealsType: String) {
        if (state.isNonInitialized()) {
            state.value = State(isLoading = true, isInitialError = false)
            if (mealsType == MealsConstants.MEALS_TYPE_SINGLES) {
                onLoadMeals()
                initMealsListChanged()
            } else if (mealsType == MealsConstants.MEALS_TYPE_LUNCHES) {
                onLoadLunches()
                initLunchesListChanged()
            }
        }
    }

    private fun onLoadMeals() {
        mealsUseCase
            .loadMeals()
            .applySchedulers()
            .map { meals -> meals.map { mealsItemUiTransformer.transform(it) } }
            .doOnSubscribe { state.value = state.nonNullValue.copy(isLoading = true) }
            .subscribe(
                {
                    state.value = state.nonNullValue.copy(
                        isLoading = false,
                        mealsList = it,
                        isInitialError = false
                    )
                },
                { throwable ->
                    Timber.e(throwable, "Error load meals")
                    processThrowable(throwable)
                    state.value = state.nonNullValue.copy(isLoading = false, isInitialError = true)
                }
            )
            .addDisposable()
    }

    private fun onLoadLunches() {
        mealsUseCase
            .loadLunches()
            .applySchedulers()
            .map { lunches -> lunches.map { lunchesItemUiTransformer.transform(it) } }
            .doOnSubscribe { state.value = state.nonNullValue.copy(isLoading = true) }
            .subscribe(
                {
                    state.value = state.nonNullValue.copy(
                        isLoading = false,
                        mealsList = it,
                        isInitialError = false
                    )
                },
                { throwable ->
                    Timber.e(throwable, "Error load lunches")
                    processThrowable(throwable)
                    state.value = state.nonNullValue.copy(isLoading = false, isInitialError = true)
                }
            )
            .addDisposable()
    }

    private fun initLunchesListChanged() {
        lunchesListChanged
            .getEventResult()
            .applySchedulers()
            .subscribe {
                onLoadLunches()
            }
            .addDisposable()
    }

    private fun initMealsListChanged() {
        mealsListChanged
            .getEventResult()
            .applySchedulers()
            .subscribe {
                onLoadMeals()
            }
            .addDisposable()
    }

    data class State(
        val mealsList: List<MealsItemUi>? = null,
        val isLoading: Boolean,
        val isInitialError: Boolean
    )
}