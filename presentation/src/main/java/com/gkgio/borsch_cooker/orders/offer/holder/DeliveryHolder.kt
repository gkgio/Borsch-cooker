package com.gkgio.borsch_cooker.orders.offer.holder

import android.view.View
import com.gkgio.borsch_cooker.orders.offer.model.DeliveryModel
import com.gkgio.borsch_cooker.view.AbstractHolder
import kotlinx.android.synthetic.main.layout_offer_delivery_holder.view.*

class DeliveryHolder(
    itemView: View
) : AbstractHolder<DeliveryModel>(itemView) {

    override fun bind(item: DeliveryModel) {
        with(itemView) {
            var address = ""
            var distance = ""
            if (item.address.city.isNotEmpty()) address += item.address.city
            if (item.address.street.isNotEmpty()) address += ", ул.${item.address.street}"
            if (item.address.house.isNotEmpty()) address += ", дом ${item.address.house}"
            if (item.address.floor.isNotEmpty()) address += ", этаж ${item.address.floor}"
            if (!item.distance.isNullOrEmpty()) distance += "~${item.distance} км"
            addressDeliveryTv.text = address
            distanceDeliveryTv.text = distance
        }
    }
}