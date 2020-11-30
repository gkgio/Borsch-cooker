package com.gkgio.borsch_cooker.orders.offer.holder

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch_cooker.orders.offer.model.MealItemModel
import com.gkgio.borsch_cooker.orders.offer.model.MealModel
import com.gkgio.borsch_cooker.view.AbstractHolder
import kotlinx.android.synthetic.main.layout_offer_meals_holder.view.*

class MealsHolder(
    itemView: View
) : AbstractHolder<MealModel>(itemView) {

    private lateinit var mealsAdapter: MealsHolderAdapter

    override fun bind(item: MealModel) {
        initMealImagesRv(itemView, item.meals)
    }

    private fun initMealImagesRv(view: View, meals: List<MealItemModel>) {
        mealsAdapter = MealsHolderAdapter()
        view.orderOfferMealRv.adapter = mealsAdapter
        view.orderOfferMealRv.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        mealsAdapter.setImagesList(meals)
    }
}