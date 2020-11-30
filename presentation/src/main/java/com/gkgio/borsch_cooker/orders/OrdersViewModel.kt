package com.gkgio.borsch_cooker.orders

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.utils.SingleLiveEvent
import com.gkgio.borsch_cooker.utils.events.NeedUpdateOrders
import javax.inject.Inject

class OrdersViewModel @Inject constructor(
    private val needUpdateOrders: NeedUpdateOrders,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val titlesLiveData = MutableLiveData<List<OrdersTypeTitle>>()
    val cancelSwipe = SingleLiveEvent<Unit>()
    private var currentPagePosition = 0
    private val typeTitles = listOf(
        OrdersTypeTitle(OrdersConstants.ORDERS_TYPE_ACTIVE, true),
        OrdersTypeTitle(OrdersConstants.ORDERS_TYPE_ALL)
    )

    init {
        titlesLiveData.value = typeTitles
    }

    fun onCurrentPagePositionChanged(position: Int) {
        currentPagePosition = position
    }

    fun onSwipedToRefresh() {
        needUpdateOrders.onComplete("")
        cancelSwipe.call()
    }
}