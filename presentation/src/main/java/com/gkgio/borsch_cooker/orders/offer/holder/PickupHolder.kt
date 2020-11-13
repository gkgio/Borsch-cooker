package com.gkgio.borsch_cooker.orders.offer.holder

import android.view.View
import com.gkgio.borsch_cooker.orders.offer.model.DeliveryModel
import com.gkgio.borsch_cooker.orders.offer.model.PickupModel
import com.gkgio.borsch_cooker.view.AbstractHolder


open class PickupHolder(itemView: View) : AbstractHolder<PickupModel>(itemView), View.OnClickListener {

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    override fun bind(item: PickupModel) {
        with(itemView) {

        }
    }
}