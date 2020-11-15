package com.gkgio.borsch_cooker.own

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.ext.applySchedulers
import com.gkgio.borsch_cooker.ext.isNonInitialized
import com.gkgio.borsch_cooker.ext.nonNullValue
import com.gkgio.borsch_cooker.navigation.Screens
import com.gkgio.borsch_cooker.orders.OrdersMealsItemUi
import com.gkgio.borsch_cooker.utils.SingleLiveEvent
import com.gkgio.borsch_cooker.utils.events.ActiveMealChanged
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.domain.own.OwnUseCase
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

class OwnViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    private val ownDashboardUiTransformer: OwnDashboardUiTransformer,
    private val ownUseCase: OwnUseCase,
    private val activeMealChanged: ActiveMealChanged,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()
    val activeMeals = MutableLiveData<List<OrdersMealsItemUi>>()
    val buttonClicked = SingleLiveEvent<String>()

    init {
        if (state.isNonInitialized()) {
            state.value = State(isLoading = true, isInitialError = false)
            loadDashboardData()
            initActiveMealsChanged()
        }
    }

    fun onProfileClicked() {
        router.navigateTo(Screens.ProfileFragmentScreen)
    }

    fun setDutyStatus(isOnDuty: Boolean) {
        ownUseCase
            .setDutyStatus(isOnDuty)
            .map { ownDashboardUiTransformer.transform(it) }
            .applySchedulers()
            .doOnSubscribe { state.value = state.nonNullValue.copy(isLoading = true) }
            .subscribe(
                {
                    state.value = state.nonNullValue.copy(
                        isLoading = false,
                        dashboard = it,
                        isInitialError = false
                    )
                    onUpdateActiveMeals(it.activeMeals, it.activeLunches)
                },
                { throwable ->
                    Timber.e(throwable, "Error send duty status request")
                    processThrowable(throwable)
                    state.value = state.nonNullValue.copy(isLoading = false, isInitialError = true)
                }
            )
            .addDisposable()
    }

    fun setDeliveryStatus(isDeliveryAvailable: Boolean) {
        ownUseCase
            .setDeliveryStatus(isDeliveryAvailable)
            .map { ownDashboardUiTransformer.transform(it) }
            .applySchedulers()
            .doOnSubscribe { state.value = state.nonNullValue.copy(isLoading = true) }
            .subscribe(
                {
                    state.value = state.nonNullValue.copy(
                        isLoading = false,
                        dashboard = it,
                        isInitialError = false
                    )
                    onUpdateActiveMeals(it.activeMeals, it.activeLunches)
                },
                { throwable ->
                    Timber.e(throwable, "Error send delivery status request")
                    processThrowable(throwable)
                    state.value = state.nonNullValue.copy(isLoading = false, isInitialError = true)
                }
            )
            .addDisposable()
    }

    fun setPickupStatus(isPickupAvailable: Boolean) {
        ownUseCase
            .setPickupStatus(isPickupAvailable)
            .map { ownDashboardUiTransformer.transform(it) }
            .applySchedulers()
            .doOnSubscribe { state.value = state.nonNullValue.copy(isLoading = true) }
            .subscribe(
                {
                    state.value = state.nonNullValue.copy(
                        isLoading = false,
                        dashboard = it,
                        isInitialError = false
                    )
                    onUpdateActiveMeals(it.activeMeals, it.activeLunches)
                },
                { throwable ->
                    Timber.e(throwable, "Error send pickup status request")
                    processThrowable(throwable)
                    state.value = state.nonNullValue.copy(isLoading = false, isInitialError = true)
                }
            )
            .addDisposable()
    }

    fun onEditActiveMealsClick() {
        router.navigateTo(Screens.ActiveMealsScreen)
    }

    private fun loadDashboardData() {
        ownUseCase
            .loadDashboardData()
            .map { ownDashboardUiTransformer.transform(it) }
            .applySchedulers()
            .doOnSubscribe { state.value = state.nonNullValue.copy(isLoading = true) }
            .subscribe(
                {
                    state.value = state.nonNullValue.copy(
                        isLoading = false,
                        dashboard = it,
                        isInitialError = false
                    )
                    onUpdateActiveMeals(it.activeMeals, it.activeLunches)
                },
                { throwable ->
                    Timber.e(throwable, "Error send dashboard request")
                    processThrowable(throwable)
                    state.value = state.nonNullValue.copy(isLoading = false, isInitialError = true)
                }
            )
            .addDisposable()
    }

    private fun onUpdateActiveMeals(
        meals: List<OrdersMealsItemUi>,
        lunches: List<OrdersMealsItemUi>
    ) {
        val activeMealsList = mutableListOf<OrdersMealsItemUi>()
        activeMealsList.addAll(meals)
        activeMealsList.addAll(lunches)
        activeMeals.value = activeMealsList
    }

    private fun initActiveMealsChanged() {
        activeMealChanged
            .getEventResult()
            .applySchedulers()
            .subscribe {
                loadDashboardData()
            }
            .addDisposable()
    }

    fun onButtonClicked(type: String) {
        buttonClicked.value = type
    }

    data class State(
        val dashboard: OwnDashboardUi? = null,
        val isLoading: Boolean,
        val isInitialError: Boolean,
        val activeMeals: List<OrdersMealsItemUi>? = null
    )

}