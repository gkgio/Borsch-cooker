package com.gkgio.borsch_cooker.orders

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.ext.isNonInitialized
import com.gkgio.borsch_cooker.navigation.Screens
import com.gkgio.domain.orders.OrdersUseCase
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen
import javax.inject.Inject

class OrdersListViewModel @Inject constructor(
    private val loadOrdersUseCase: OrdersUseCase,
    private val ordersListItemUiTransformer: OrdersListItemUiTransformer,
    baseScreensNavigator: BaseScreensNavigator,
    private val router: Router
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()
    private lateinit var ordersType: String

    //for test start
    var list = mutableListOf<OrdersListItemUi>()
    var mealsList = mutableListOf<OrdersMealsItemUi>()
    var imagesList = mutableListOf<String>()
    var imagesList2 = mutableListOf<String>()
    //for test end

    fun init(ordersType: String) {
        this.ordersType = ordersType
        //for test start
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
                "",
                "2",
                "0",
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
                1,
                500,
                "",
                "",
                "2",
                "0",
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
                1,
                500,
                "",
                "",
                "2",
                "0",
                imagesList2
            )
        )

        list.add(
            OrdersListItemUi(
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
        )
        list.add(
            OrdersListItemUi(
                "1",
                "2",
                "3",
                "created",
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
                    "Сегодня, попозже",
                    "",
                    true
                ),
                mealsList,
                listOf()
            )
        )
        //for test stop

        if (state.isNonInitialized()) {
            state.value = State(isLoading = true, isInitialError = false, ordersList = list)
            // onLoadOrders()
        }
    }
/*
    fun onLoadOrders() {
        loadOrdersUseCase
            .loadOrdersData(ordersType)
            .map { ordersListItemUiTransformer.transform(it) }
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
                {
                    Timber.e(it, "Error load orders")
                    state.value = state.nonNullValue.copy(isLoading = false, isInitialError = true)
                }
            )
            .addDisposable()
    }
 */

    fun clickOrder(orderId: String) {
        router.navigateTo(Screens.OrderDetailsScreen(orderId))
    }

    data class State(
        val ordersList: List<OrdersListItemUi>,
        val isLoading: Boolean,
        val isInitialError: Boolean
    )
}