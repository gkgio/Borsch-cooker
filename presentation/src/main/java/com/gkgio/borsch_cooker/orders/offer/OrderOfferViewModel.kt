package com.gkgio.borsch_cooker.orders.offer

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.orders.OrdersAddressItemUi
import com.gkgio.borsch_cooker.orders.OrdersListItemUi
import com.gkgio.borsch_cooker.orders.offer.model.ClientModel
import com.gkgio.borsch_cooker.orders.offer.model.DeliveryModel
import com.gkgio.borsch_cooker.orders.offer.model.MealModel
import com.gkgio.borsch_cooker.orders.offer.model.PickupModel
import com.gkgio.borsch_cooker.utils.SingleLiveEvent
import kotlinx.android.synthetic.main.fragment_order_offer.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class OrderOfferViewModel @Inject constructor(
    screensNavigator: BaseScreensNavigator,
    private val router: Router
) : BaseViewModel(screensNavigator) {

    val state = MutableLiveData<State>()
    val activeTime = SingleLiveEvent<Time>()
    val sheetClick = SingleLiveEvent<Info>()
    var timer: CountDownTimer? = null

    // public

    fun init(order: String?) {
        val test = mutableListOf<Any>()
        val testAd = OrdersAddressItemUi("", "", "", "Членогорск", "", "", "", "4 эт.", "33", "", "", "Малаховская", 0L, "", "", "", false)
        test.add(MealModel(listOf("Как купить")))
        test.add(PickupModel("Сам заберу!"))
        test.add(DeliveryModel(testAd))
        test.add(ClientModel(1, "Излишне", "Путило Стародубов"))
        state.value = State(test, false, false)
        initTimer()
    }

    fun onAcceptClick() {

    }

    fun onDeclineClick() {
        router.exit()
        //TODO onOrderCancelled()
    }

    fun onSheetClick(type: String) {

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
                //TODO onOrderCancelled()
            }
        }
        timer?.start()
    }

    data class State(
        val offerOrder: List<Any>,
        val isLoading: Boolean,
        val isError: Boolean
    )

    data class Time(
        val activeTimer: Int,
        val activeTime: String
    )

    data class Info(
        val type: String,
        val params: Any
    )
}