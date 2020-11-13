package com.gkgio.borsch_cooker.orders.offer.holder

import android.view.View
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.borsch_cooker.orders.offer.model.MealModel
import com.gkgio.borsch_cooker.view.AbstractHolder


open class MealsHolder(
    itemView: View,
    private val itemClick: () -> Unit
) : AbstractHolder<MealModel>(itemView) {

    override fun bind(item: MealModel) {
        with(itemView) {
            setDebounceOnClickListener {
                itemClick()
            }
        }
    }
}