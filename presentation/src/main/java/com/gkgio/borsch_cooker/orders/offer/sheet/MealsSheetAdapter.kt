package com.gkgio.borsch_cooker.orders.offer.sheet

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.ext.getQuantityText
import com.gkgio.borsch_cooker.ext.withCenterCropRoundedCorners
import com.gkgio.borsch_cooker.orders.offer.model.MealItemModel
import com.gkgio.borsch_cooker.utils.getImageUrl
import com.gkgio.borsch_cooker.view.SyntheticViewHolder
import kotlinx.android.synthetic.main.layout_orders_meal_view_holder.view.*

class MealsSheetAdapter : RecyclerView.Adapter<SyntheticViewHolder>() {

    private var meals = listOf<MealItemModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder {
        return SyntheticViewHolder.inflateFrom(parent, R.layout.layout_orders_meal_view_holder)
    }

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) =
        with(holder.itemView) {
            Glide.with(ordersMealImageView)
                .load(getImageUrl(meals[position].image))
                .placeholder(R.drawable.ic_image_placeholder)
                .withCenterCropRoundedCorners(
                    context,
                    context.resources.getInteger(R.integer.corner_radius_small_image)
                )
                .into(ordersMealImageView)
            ordersMealName.text = meals[position].name
            ordersPortionsCount.isVisible = true
            ordersPortionsCount.text =
                context.getQuantityText(R.plurals.portion_counter, meals[position].portions)
        }

    override fun getItemCount(): Int = meals.size

    fun setMealsList(meals: List<MealItemModel>) {
        this.meals = meals
        notifyDataSetChanged()
    }

}