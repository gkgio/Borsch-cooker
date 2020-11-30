package com.gkgio.borsch_cooker.orders.offer

import android.os.CountDownTimer
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.ext.applySchedulers
import com.gkgio.borsch_cooker.ext.isNonInitialized
import com.gkgio.borsch_cooker.orders.OrdersConstants
import com.gkgio.borsch_cooker.orders.OrdersListItemUi
import com.gkgio.borsch_cooker.orders.offer.model.*
import com.gkgio.borsch_cooker.utils.SingleLiveEvent
import com.gkgio.domain.orderdetails.OrderDetailsUseCase
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class OrderOfferViewModel @Inject constructor(
    private val router: Router,
    private val ordersDetailsUseCase: OrderDetailsUseCase,
    private val ordersToMealsUiTransformer: OrdersToMealsUiTransformer,
    screensNavigator: BaseScreensNavigator
) : BaseViewModel(screensNavigator) {

    val orderInfo = SingleLiveEvent<Order>()
    val activeTime = SingleLiveEvent<Time>()
    val isLoading = SingleLiveEvent<Boolean>()
    var timer: CountDownTimer? = null
    private lateinit var order: OrdersListItemUi

    // public

    fun init(order: OrdersListItemUi) {
        this.order = order
        orderInfo.value = Order(createOfferList(order), order.price.toString())
        initTimer()
        if (isLoading.isNonInitialized()) isLoading.value = false
    }

    fun onAcceptClick() {
        onChangeOrderStatus(order.id, OrdersConstants.ORDERS_STATUS_ACCEPTED)
    }

    fun onDeclineClick() {
        onChangeOrderStatus(order.id, OrdersConstants.ORDERS_STATUS_CANCELED)
    }

    //overrides

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    // private

    private fun initTimer() {
        timer = object : CountDownTimer(OrderOfferFragment.TIMER_ACTIVE, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val min = (millisUntilFinished.toInt() / 1000) / 60
                val sec = (millisUntilFinished.toInt() / 1000) % 60
                activeTime.postValue(
                    Time(
                        millisUntilFinished.toInt(),
                        "$min:${if (sec < 10) "0$sec" else sec.toString()}"
                    )
                )
            }

            override fun onFinish() {
                router.exit()
            }
        }
        timer?.start()
    }

    private fun createOfferList(order: OrdersListItemUi): List<Any> {
        val list = mutableListOf<Any>()
        val meals = mutableListOf<MealItemModel>()
        meals.addAll(ordersToMealsUiTransformer.transform(order.meals))
        list.add(MealModel(meals))
        if (order.type == OrdersConstants.ORDERS_TAKE_DELIVERY) {
            list.add(DeliveryModel(order.address, null))
        } else {
            list.add(PickupModel())
        }

        list.add(ClientModel(0, null, null))
        return list
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
                router.exit()
            }, {
                isLoading.value = false
                processThrowable(it)
            })
            .addDisposable()
    }

    data class Order(
        val offerOrder: List<Any>,
        val price: String
    )

    data class Time(
        val activeTimer: Int,
        val activeTime: String
    )
}