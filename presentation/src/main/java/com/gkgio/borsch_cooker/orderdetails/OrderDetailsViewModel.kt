package com.gkgio.borsch_cooker.orderdetails

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.ext.applySchedulers
import com.gkgio.borsch_cooker.ext.isNonInitialized
import com.gkgio.borsch_cooker.ext.nonNullValue
import com.gkgio.borsch_cooker.orders.OrdersAddressItemUi
import com.gkgio.borsch_cooker.orders.OrdersListItemUi
import com.gkgio.borsch_cooker.orders.OrdersListItemUiTransformer
import com.gkgio.borsch_cooker.orders.OrdersMealsItemUi
import com.gkgio.domain.orderdetails.OrderDetailsUseCase
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

class OrderDetailsViewModel @Inject constructor(
    private val loadOrderDetailsUseCase: OrderDetailsUseCase,
    private val ordersListItemUiTransformer: OrdersListItemUiTransformer,
    baseScreensNavigator: BaseScreensNavigator,
    private val router: Router
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()
    private lateinit var orderId: String

    fun init(orderId: String) {
        this.orderId = orderId
        //for test start
        var mealsList = mutableListOf<OrdersMealsItemUi>()
        var imagesList = mutableListOf<String>()
        var imagesList2 = mutableListOf<String>()
        imagesList.add("https://img.povar.ru/main/ab/23/b4/9c/samii_vkusnii_borsh-404089.jpg")
        imagesList2.add("https://www.gastronom.ru/binfiles/images/00000192/00072755.jpg")
        mealsList.add(
            OrdersMealsItemUi(
                "0",
                true,
                111,
                1,
                "",
                listOf(),
                "Борщ",
                1,
                500,
                "",
                "2",
                imagesList
            )
        )
        mealsList.add(
            OrdersMealsItemUi(
                "0",
                true,
                111,
                1,
                "",
                listOf(),
                "Борщ",
                5,
                500,
                "",
                "2",
                imagesList
            )
        )
        mealsList.add(
            OrdersMealsItemUi(
                "0",
                true,
                111,
                1,
                "",
                listOf(),
                "Макароны по-флотски",
                2,
                500,
                "",
                "2",
                imagesList2
            )
        )

        val order = OrdersListItemUi(
            "1",
            "2",
            "3",
            "accepted",
            OrdersAddressItemUi(
                "1",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                0,
                "2020-05-23T07:14:23.940Z",
                "Когда-нибудь",
                "",
                true
            ),
            mealsList,
            listOf()
        )
        //for test stop

        if (state.isNonInitialized()) {
            state.value = State(
                orderDetails = order, isLoading = false, isInitialError = false
            )
            //onLoadOrderDetails()
        }
    }

    fun onLoadOrderDetails() {
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

    fun clickLeftIcon() {
        router.exit()
    }

    data class State(
        val orderDetails: OrdersListItemUi,
        val isLoading: Boolean,
        val isInitialError: Boolean
    )
}