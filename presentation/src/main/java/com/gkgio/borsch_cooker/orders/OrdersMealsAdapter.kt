package com.gkgio.borsch_cooker.orders

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.ext.getQuantityText
import com.gkgio.borsch_cooker.ext.withCenterCropRoundedCorners
import com.gkgio.borsch_cooker.utils.getImageUrl
import com.gkgio.borsch_cooker.view.SyntheticViewHolder
import kotlinx.android.synthetic.main.layout_orders_meal_view_holder.view.*

class OrdersMealsAdapter(
    private val showPortions: Boolean
) : RecyclerView.Adapter<SyntheticViewHolder>() {

    private var mealsList = listOf<OrdersMealsItemUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder =
        SyntheticViewHolder.inflateFrom(parent, R.layout.layout_orders_meal_view_holder)

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) =
        with(holder.itemView) {
            val meal = mealsList[position]
            if (!meal.images.isNullOrEmpty()) {
                Glide.with(ordersMealImageView)
                    .load(getImageUrl(meal.images[0]))
                    .placeholder(R.drawable.ic_image_placeholder)
                    .withCenterCropRoundedCorners(
                        context,
                        context.resources.getInteger(R.integer.corner_radius_small_image)
                    )
                    .into(ordersMealImageView)
            }
            ordersMealName.text = mealsList[position].name
            ordersPortionsCount.isVisible = showPortions
            ordersPortionsCount.text =
                context.getQuantityText(R.plurals.portion_counter, mealsList[position].portions)
        }

    fun setMealsList(mealsList: List<OrdersMealsItemUi>?) {
        val list = mealsList ?: listOf()
        this.mealsList = list
        notifyItemRangeInserted(this.mealsList.size, list.size)
    }
}