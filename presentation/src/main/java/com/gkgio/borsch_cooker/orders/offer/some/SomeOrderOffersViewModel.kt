package com.gkgio.borsch_cooker.orders.offer.some

import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.ext.applySchedulers
import com.gkgio.borsch_cooker.orders.OrdersConstants
import com.gkgio.borsch_cooker.orders.OrdersListItemUi
import com.gkgio.borsch_cooker.utils.SingleLiveEvent
import com.gkgio.borsch_cooker.utils.events.StartStopCatchOffers
import com.gkgio.domain.orderdetails.OrderDetailsUseCase
import javax.inject.Inject

class SomeOrderOffersViewModel @Inject constructor(
    private val ordersDetailsUseCase: OrderDetailsUseCase,
    private val startStopCatchOffers: StartStopCatchOffers,
    screensNavigator: BaseScreensNavigator
) : BaseViewModel(screensNavigator) {

    val orders = SingleLiveEvent<List<OrdersListItemUi>>()
    val isLoading = SingleLiveEvent<Boolean>()
    val closeDialog = SingleLiveEvent<Unit>()
    private var ordersList = mutableListOf<OrdersListItemUi>()

    fun init(orders: List<OrdersListItemUi>) {
        ordersList = orders.toMutableList()
        this.orders.value = ordersList
    }

    fun onOrderActionClicked(orderId: String, action: String) {
        val status = when (action) {
            SomeOrderOffersSheet.BUTTON_ACCEPT -> OrdersConstants.ORDERS_STATUS_ACCEPTED
            SomeOrderOffersSheet.BUTTON_DECLINE -> OrdersConstants.ORDERS_STATUS_CANCELED
            else -> throw Throwable("Unknown action $action")
        }
        onChangeOrderStatus(orderId, status)
    }

    fun onStartCatchOffers() {
        startStopCatchOffers.onComplete("start")
    }

    fun onStopCatchOffers() {
        startStopCatchOffers.onComplete("stop")
    }

    private fun onChangeOrderStatus(orderId: String, status: String) {
        ordersDetailsUseCase
            .changeOrderStatus(orderId, status)
            .applySchedulers()
            .doOnSubscribe {
                isLoading.value = true
            }
            .subscribe({
                isLoading.value = false
                ordersList.remove(ordersList.first { it.id == orderId })
                if (ordersList.size > 0) {
                    orders.value = ordersList
                } else {
                    closeDialog.call()
                }
            }, {
                isLoading.value = false
                processThrowable(it)
            })
            .addDisposable()
    }
}