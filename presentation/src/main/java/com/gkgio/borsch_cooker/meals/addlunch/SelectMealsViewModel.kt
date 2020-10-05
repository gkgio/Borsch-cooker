package com.gkgio.borsch_cooker.meals.addlunch

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.ext.applySchedulers
import com.gkgio.borsch_cooker.ext.isNonInitialized
import com.gkgio.borsch_cooker.ext.nonNullValue
import com.gkgio.borsch_cooker.meals.MealsItemUi
import com.gkgio.borsch_cooker.meals.MealsItemUiTransformer
import com.gkgio.borsch_cooker.utils.SingleLiveEvent
import com.gkgio.borsch_cooker.utils.events.LunchMealsChanged
import com.gkgio.domain.meals.MealsUseCase
import timber.log.Timber
import javax.inject.Inject

class SelectMealsViewModel @Inject constructor(
    screensNavigator: BaseScreensNavigator,
    private val mealsUseCase: MealsUseCase,
    private val lunchMealsChanged: LunchMealsChanged,
    private val mealsItemUiTransformer: MealsItemUiTransformer
) : BaseViewModel(screensNavigator) {

    val state = MutableLiveData<State>()
    val checkedMeals = SingleLiveEvent<MutableList<MealsItemUi>>()
    private val checkedList = mutableListOf<MealsItemUi>()

    init {
        state.value = State(isLoading = false, isInitialError = false)
        onLoadMeals()
        checkedMeals.value = checkedList
    }

    private fun onLoadMeals() {
        mealsUseCase
            .loadMeals()
            .applySchedulers()
            .map { data -> data.map { mealsItemUiTransformer.transform(it) } }
            .doOnSubscribe { state.value = State(isLoading = true, isInitialError = false) }
            .subscribe({
                state.value = state.nonNullValue.copy(isLoading = false, mealsList = it)
            }, { throwable ->
                Timber.e(throwable, "Error load meals")
                processThrowable(throwable)
                state.value = state.nonNullValue.copy(isLoading = false, isInitialError = true)
            })
            .addDisposable()
    }

    fun onChangeCheckedMeals(id: String, checked: Boolean) {
        if (checked) {
            checkedList.add(state.value?.mealsList?.find { it.id == id }!!)
        } else {
            checkedList.remove(state.value?.mealsList?.find { it.id == id })
        }
        checkedMeals.value = checkedList
        lunchMealsChanged.onComplete(checkedMeals.value!!)

    }

    data class State(
        val isLoading: Boolean,
        val isInitialError: Boolean,
        val mealsList: List<MealsItemUi>? = null
    )
}