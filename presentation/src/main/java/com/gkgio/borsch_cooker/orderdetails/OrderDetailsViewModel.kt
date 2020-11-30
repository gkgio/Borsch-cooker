package com.gkgio.borsch_cooker.orderdetails

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.ext.applySchedulers
import com.gkgio.borsch_cooker.ext.isNonInitialized
import com.gkgio.borsch_cooker.ext.nonNullValue
import com.gkgio.borsch_cooker.navigation.Screens
import com.gkgio.borsch_cooker.orders.OrdersListItemUi
import com.gkgio.borsch_cooker.orders.OrdersListItemUiTransformer
import com.gkgio.borsch_cooker.utils.SingleLiveEvent
import com.gkgio.borsch_cooker.utils.events.NeedUpdateOrders
import com.gkgio.domain.orderdetails.OrderDetailsUseCase
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

class OrderDetailsViewModel @Inject constructor(
        private val loadOrderDetailsUseCase: OrderDetailsUseCase,
        private val ordersListItemUiTransformer: OrdersListItemUiTransformer,
        private val router: Router,
        private val orderDetailsUseCase: OrderDetailsUseCase,
        private val needUpdateOrders: NeedUpdateOrders,
        baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()
    val status = SingleLiveEvent<String>()
    private lateinit var orderId: String

    //public

    fun init(orderId: String) {
        this.orderId = orderId
        if (state.isNonInitialized()) {
            state.value = State(
                    isLoading = false, isInitialError = false
            )
            onLoadOrderDetails()
        }
    }

    fun onClientChatClicked() {
        with(state.nonNullValue.orderDetails) {
            if (this != null) {
                router.navigateTo(Screens.OrderChatFragmentScreen(orderId, cookerId))
            }
        }
    }

    fun clickLeftIcon() {
        router.exit()
    }

    fun onChangeStatusClicked() {
        status.value = state.value?.orderDetails?.status
    }

    fun onStatusChanged(status: String) {
        onChangeOrderStatus(status)
    }

    //private

    private fun onLoadOrderDetails() {
        loadOrderDetailsUseCase
                .loadOrderDetailsData(orderId)
                .map { ordersListItemUiTransformer.transform(it) }
                .applySchedulers()
                .doOnSubscribe { state.value = state.nonNullValue.copy(isLoading = true) }
                .subscribe(
                        {
                            state.value = state.nonNullValue.copy(
                                    isLoading = false,
                                    orderDetails = it,
                                    isInitialError = false
                            )
                        },
                        {
                            Timber.e(it, "Error load order details")
                            state.value = state.nonNullValue.copy(isLoading = false, isInitialError = true)
                        }
                )
                .addDisposable()
    }

    private fun onChangeOrderStatus(status: String) {
        orderDetailsUseCase
                .changeOrderStatus(orderId, status)
                .map { ordersListItemUiTransformer.transform(it) }
                .applySchedulers()
                .doOnSubscribe { state.value = state.nonNullValue.copy(isLoading = true) }
                .subscribe({
                    state.value = state.nonNullValue.copy(
                            isLoading = false,
                            orderDetails = it,
                            isInitialError = false
                    )
                    needUpdateOrders.onComplete("")
                }, {
                    processThrowable(it)
                })
                .addDisposable()
    }

    data class State(
            val orderDetails: OrdersListItemUi? = null,
            val isLoading: Boolean,
            val isInitialError: Boolean
    )
}