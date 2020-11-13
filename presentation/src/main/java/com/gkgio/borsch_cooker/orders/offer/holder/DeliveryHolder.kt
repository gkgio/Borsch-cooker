package com.gkgio.borsch_cooker.orders.offer.holder

import android.view.View
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.borsch_cooker.orders.offer.model.DeliveryModel
import com.gkgio.borsch_cooker.view.AbstractHolder
import kotlinx.android.synthetic.main.layout_offer_delivery_holder.view.*

open class DeliveryHolder(
    itemView: View,
    private val itemClick: () -> Unit
) : AbstractHolder<DeliveryModel>(itemView) {

    override fun bind(item: DeliveryModel) {
        with(itemView) {
            addressDeliveryTv.text =
                "${item.address.city}, ${item.address.street}, ${item.address.house}, ${item.address.floor}"
            setDebounceOnClickListener {
                itemClick()
            }
        }
    }
}