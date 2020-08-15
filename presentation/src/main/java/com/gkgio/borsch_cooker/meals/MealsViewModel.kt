package com.gkgio.borsch_cooker.meals

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.orders.OrdersTypeTitle
import javax.inject.Inject

class MealsViewModel @Inject constructor(
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val titlesLiveData = MutableLiveData<List<OrdersTypeTitle>>()
    private var currentPagePosition = 0
    private val typeTitles = listOf(
        OrdersTypeTitle(MealsConstants.MEALS_TYPE_SINGLES, true),
        OrdersTypeTitle(MealsConstants.MEALS_TYPE_LUNCHES)
    )

    init {
        titlesLiveData.value = typeTitles
    }

    fun onCurrentPagePositionChanged(position: Int) {
        currentPagePosition = position
    }

}