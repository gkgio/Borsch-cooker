package com.gkgio.borsch_cooker.own.activemeals

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.ext.applySchedulers
import com.gkgio.borsch_cooker.ext.isNonInitialized
import com.gkgio.borsch_cooker.ext.nonNullValue
import com.gkgio.borsch_cooker.orders.OrdersMealsItemUi
import com.gkgio.borsch_cooker.utils.events.ActiveMealChanged
import com.gkgio.domain.meals.MealsUseCase
import timber.log.Timber
import javax.inject.Inject

class ActiveMealsViewModel @Inject constructor(
    private val mealsUseCase: MealsUseCase,
    private val activeMealChanged: ActiveMealChanged,
    private val activeMealsUiTransformer: ActiveMealsUiTransformer,
    private val activeLunchesUiTransformer: ActiveLunchesUiTransformer,
    screensNavigator: BaseScreensNavigator
) : BaseViewModel(screensNavigator) {

    val state = MutableLiveData<State>()

    init {
        if (state.isNonInitialized()) {
            state.value = State(isLoading = false, isInitialError = false)
            uploadMeals()
           // uploadLunches() TODO в первой итерации не нужно
        }
    }

    //public

    fun onItemClick(id: String, action: Int, isActive: Boolean?) {
        when (action) {
            ActiveMealsFragment.ACTION_INFO -> infoClick()
            ActiveMealsFragment.ACTION_MINUS -> minusPortionClick(id)
            ActiveMealsFragment.ACTION_PLUS -> plusPortionClick(id)
            ActiveMealsFragment.ACTION_MEAL_STATE -> onChangeMealState(id, isActive)
        }
    }

    //private

    private fun plusPortionClick(id: String) {
        mealsUseCase
            .incrementPortion(id)
            .applySchedulers()
            .subscribe({
                activeMealChanged.onComplete("")
            }, { throwable ->
                Timber.e(throwable, "Error send increment request")
                processThrowable(throwable)
                state.value = state.nonNullValue.copy(isLoading = false, isInitialError = true)
            })
            .addDisposable()
    }

    private fun minusPortionClick(id: String) {
        mealsUseCase
            .decrementPortion(id)
            .applySchedulers()
            .subscribe({
                activeMealChanged.onComplete("")
            }, { throwable ->
                Timber.e(throwable, "Error send decrement request")
                processThrowable(throwable)
                state.value = state.nonNullValue.copy(isLoading = false, isInitialError = true)
            })
            .addDisposable()
    }

    private fun infoClick() {
        //TODO
    }

    private fun onChangeMealState(id: String, isActive: Boolean?) {
        isActive?.let {
            mealsUseCase
                .setAvailabilityMeal(id, isActive)
                .applySchedulers()
                .subscribe({
                    activeMealChanged.onComplete("")
                }, { throwable ->
                    Timber.e(throwable, "Error send meal state request")
                    processThrowable(throwable)
                    state.value = state.nonNullValue.copy(isLoading = false, isInitialError = true)
                })
        }
    }

    private fun uploadMeals() {
        mealsUseCase
            .loadMeals()
            .map { meals -> meals.map { activeMealsUiTransformer.transform(it) } }
            .applySchedulers()
            .doOnSubscribe { state.value = state.nonNullValue.copy(isLoading = true) }
            .subscribe(
                {
                    state.value = state.nonNullValue.copy(
                        isLoading = false,
                        activeMeals = onUpdateActiveMeals(it, false),
                        isInitialError = false
                    )
                },
                { throwable ->
                    Timber.e(throwable, "Error upload meals")
                    processThrowable(throwable)
                    state.value = state.nonNullValue.copy(isLoading = false, isInitialError = true)
                }
            )
            .addDisposable()
    }

    private fun uploadLunches() {
        mealsUseCase
            .loadLunches()
            .map { meals -> meals.map { activeLunchesUiTransformer.transform(it) } }
            .applySchedulers()
            .doOnSubscribe { state.value = state.nonNullValue.copy(isLoading = true) }
            .subscribe(
                {
                    state.value = state.nonNullValue.copy(
                        isLoading = false,
                        activeMeals = onUpdateActiveMeals(it, true),
                        isInitialError = false
                    )
                },
                { throwable ->
                    Timber.e(throwable, "Error upload meals")
                    processThrowable(throwable)
                    state.value = state.nonNullValue.copy(isLoading = false, isInitialError = true)
                }
            )
            .addDisposable()
    }

    private fun onUpdateActiveMeals(
        meals: List<OrdersMealsItemUi>,
        isLunch: Boolean
    ): List<OrdersMealsItemUi> {
        val activeMealsList = state.value?.activeMeals?.toMutableList()
        meals.forEach {
            activeMealsList?.add(
                OrdersMealsItemUi(
                    it.id,
                    it.available,
                    it.calories,
                    it.cookTime,
                    it.description,
                    it.ingredients,
                    it.name,
                    it.portions,
                    it.price,
                    it.weight,
                    it.cookerId,
                    it.images,
                    isLunch
                )
            )
        }
        return activeMealsList!!
    }

    data class State(
        val activeMeals: List<OrdersMealsItemUi> = listOf(),
        val isLoading: Boolean,
        val isInitialError: Boolean
    )

}