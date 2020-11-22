package com.gkgio.borsch_cooker.orders.offer.sheet

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.ext.isNonInitialized
import com.gkgio.borsch_cooker.orders.offer.model.DeliveryModel
import javax.inject.Inject

class DeliverySheetViewModel @Inject constructor(
        screensNavigator: BaseScreensNavigator
) : BaseViewModel(screensNavigator) {

    val state = MutableLiveData<State>()

    fun init(delivery: DeliveryModel) {
        if (state.isNonInitialized()) {
            var address = ""
            var distance = ""
            if (delivery.address != null) {
                if (delivery.address.city.isNotEmpty()) address += delivery.address.city
                if (delivery.address.street.isNotEmpty()) address += ", ул.${delivery.address.street}"
                if (delivery.address.house.isNotEmpty()) address += ", дом ${delivery.address.house}"
                if (!delivery.distance.isNullOrEmpty()) distance += "~${delivery.distance} км"
            }
            state.value = State(address, distance)
        }
    }

    data class State(
            val address: String,
            val distance: String
    )
}