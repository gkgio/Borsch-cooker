package com.gkgio.borsch_cooker.orders

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import javax.inject.Inject

class OrdersViewModel @Inject constructor(
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val titlesLiveData = MutableLiveData<List<OrdersTypeTitle>>()
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
}