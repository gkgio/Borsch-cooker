package com.gkgio.borsch_cooker.meals.addlunch

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.borsch_cooker.ext.withCenterCropRoundedCorners
import com.gkgio.borsch_cooker.meals.MealsItemUi
import com.gkgio.borsch_cooker.utils.getImageUrl
import com.gkgio.borsch_cooker.view.SyntheticViewHolder
import kotlinx.android.synthetic.main.layout_lunch_meals_view_holder.view.*

class AddLunchMealsAdapter(
    private val itemClick: (id: String) -> Unit
) : RecyclerView.Adapter<SyntheticViewHolder>() {

    private var mealsList = listOf<MealsItemUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder {
        return SyntheticViewHolder.inflateFrom(parent, R.layout.layout_lunch_meals_view_holder)
    }

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) =
        with(holder.itemView) {
            val meal = mealsList[position]
            Glide.with(lunchMealsIv)
                .load(if (meal.images.isEmpty()) null else getImageUrl(meal.images.first()))
                .placeholder(R.drawable.ic_image_placeholder)
                .withCenterCropRoundedCorners(
                    context,
                    context.resources.getInteger(R.integer.corner_radius_small_image)
                )
                .into(lunchMealsIv)
            lunchMealsNameTv.text = meal.name
            lunchMealsPriceTv.text = context.getString(R.string.orders_sum, meal.price.toString())
            addLunchMealCountTv.text = (position + 1).toString()
            addLunchDeleteMealTv.setDebounceOnClickListener {
                itemClick(meal.id)
            }
        }

    override fun getItemCount(): Int = mealsList.size

    fun setMealsList(mealsList: List<MealsItemUi>) {
        this.mealsList = mealsList
        notifyDataSetChanged()
    }

}