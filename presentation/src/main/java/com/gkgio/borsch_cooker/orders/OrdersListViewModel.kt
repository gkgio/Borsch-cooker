package com.gkgio.borsch_cooker.orders

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.ext.applySchedulers
import com.gkgio.borsch_cooker.ext.isNonInitialized
import com.gkgio.borsch_cooker.ext.nonNullValue
import com.gkgio.borsch_cooker.navigation.Screens
import com.gkgio.borsch_cooker.utils.events.NeedUpdateOrders
import com.gkgio.domain.orders.OrdersUseCase
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

class OrdersListViewModel @Inject constructor(
    private val loadOrdersUseCase: OrdersUseCase,
    private val ordersListItemUiTransformer: OrdersListItemUiTransformer,
    private val needUpdateOrders: NeedUpdateOrders,
    private val router: Router,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()
    private lateinit var ordersType: String

    //public

    fun init(ordersType: String) {

        this.ordersType = ordersType

        if (state.isNonInitialized()) {
            state.value = State(isLoading = true, isInitialError = false, ordersList = listOf())
            if (ordersType == OrdersConstants.ORDERS_TYPE_ALL) {
                onLoadAllOrders()
            } else {
                onLoadActiveOrders()
            }
            initNeedUpdateOrders()
        }
    }

    fun clickOrder(orderId: String) {
        router.navigateTo(Screens.OrderDetailsScreen(orderId))
    }

    //private

    private fun onLoadActiveOrders() {
        loadOrdersUseCase
            .loadActiveOrdersData()
            .map { loadActiveOrdersData ->
                loadActiveOrdersData.map {
                    ordersListItemUiTransformer.transform(it)
                }
            }
            .applySchedulers()
            .doOnSubscribe { state.value = state.nonNullValue.copy(isLoading = true) }
            .subscribe(
                {
                    state.value = state.nonNullValue.copy(
                        isLoading = false,
                        ordersList = it,
                        isInitialError = false
                    )
                },
                { throwable ->
                    Timber.e(throwable, "Error load active orders")
                    processThrowable(throwable)
                    state.value = state.nonNullValue.copy(isLoading = false, isInitialError = true)
                }
            )
            .addDisposable()
    }

    private fun onLoadAllOrders() {
        loadOrdersUseCase
            .loadAllOrdersData()
            .map { loadAllOrdersData ->
                loadAllOrdersData.map {
                    ordersListItemUiTransformer.transform(it)
                }
            }
            .applySchedulers()
            .doOnSubscribe { state.value = state.nonNullValue.copy(isLoading = true) }
            .subscribe(
                {
                    state.value = state.nonNullValue.copy(
                        isLoading = false,
                        ordersList = it,
                        isInitialError = false
                    )
                },
                { throwable ->
                    Timber.e(throwable, "Error load all orders")
                    processThrowable(throwable)
                    state.value = state.nonNullValue.copy(isLoading = false, isInitialError = true)
                }
            )
            .addDisposable()
    }

    private fun initNeedUpdateOrders() {
        needUpdateOrders
            .getEventResult()
            .applySchedulers()
            .subscribe {
                if (ordersType == OrdersConstants.ORDERS_TYPE_ALL) {
                    onLoadAllOrders()
                } else {
                    onLoadActiveOrders()
                }
            }
            .addDisposable()
    }

    data class State(
        val ordersList: List<OrdersListItemUi>,
        val isLoading: Boolean,
        val isInitialError: Boolean
    )
}