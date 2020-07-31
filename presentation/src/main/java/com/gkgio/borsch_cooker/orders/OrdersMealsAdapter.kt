package com.gkgio.borsch_cooker.orders

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.borsch_cooker.ext.withCenterCropRoundedCorners
import com.gkgio.borsch_cooker.utils.getPortionsTitleByPortionsCount
import com.gkgio.borsch_cooker.view.SyntheticViewHolder
import kotlinx.android.synthetic.main.layout_orders_meal_view_holder.view.*

class OrdersMealsAdapter(
    private var mealsList: List<OrdersMealsItemUi>,
    private val showPortions: Boolean,
    val itemClick: () -> Unit
) : RecyclerView.Adapter<SyntheticViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder {
        val holder =
            SyntheticViewHolder.inflateFrom(parent, R.layout.layout_orders_meal_view_holder)

        holder.itemView.setDebounceOnClickListener {
            itemClick()
        }

        return holder
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) =
        with(holder.itemView) {
            val meal = mealsList[position]
            Glide.with(ordersMealImageView)
                .load(meal.images[0])
                .placeholder(R.drawable.ic_image_placeholder)
                .withCenterCropRoundedCorners(context, 8)
                .into(ordersMealImageView)
            ordersMealName.text = mealsList[position].name
            ordersPortionsCount.isVisible = showPortions
            ordersPortionsCount.text =
                getPortionsTitleByPortionsCount(context, mealsList[position].portions)
        }

    fun setMealsList(mealsList: List<OrdersMealsItemUi>) {
        this.mealsList = mealsList
        notifyDataSetChanged()
    }
}