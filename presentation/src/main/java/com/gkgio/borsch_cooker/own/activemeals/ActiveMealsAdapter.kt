package com.gkgio.borsch_cooker.own.activemeals

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.borsch_cooker.ext.withCenterCropRoundedCorners
import com.gkgio.borsch_cooker.orders.OrdersMealsItemUi
import com.gkgio.borsch_cooker.utils.getImageUrl
import com.gkgio.borsch_cooker.view.SyntheticViewHolder
import kotlinx.android.synthetic.main.layout_active_meals_view_holder.view.*

class ActiveMealsAdapter(private val itemClick: (id: String, action: Int, isActive: Boolean?) -> Unit) :
    RecyclerView.Adapter<SyntheticViewHolder>() {

    private var activeMeals = listOf<OrdersMealsItemUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder {
        return SyntheticViewHolder.inflateFrom(parent, R.layout.layout_active_meals_view_holder)
    }

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) =
        with(holder.itemView) {
            val meal = activeMeals[position]
            var portions = meal.portions
            if (!meal.images.isNullOrEmpty()) {
                Glide.with(activeMealIv)
                    .load(getImageUrl(meal.images.first()))
                    .placeholder(R.drawable.ic_image_placeholder)
                    .withCenterCropRoundedCorners(
                        context,
                        context.resources.getInteger(R.integer.corner_radius_small_image)
                    )
                    .into(activeMealIv)
            }
            meal.isLunch?.let {
                portionPlusIv.isVisible = !it
                portionMinusIv.isVisible = !it
                comboInfoTv.isVisible = it
            }
            portionCountTv.text = portions.toString()
            activeMealTv.text = meal.name
            mealIsActive.isChecked = meal.available
            mealIsActive.setDebounceOnClickListener {
                itemClick(meal.id, ActiveMealsFragment.ACTION_MEAL_STATE, mealIsActive.isChecked)
            }
            portionPlusIv.setDebounceOnClickListener {
                portions++
                portionCountTv.text = portions.toString()
                itemClick(meal.id, ActiveMealsFragment.ACTION_PLUS, null)
            }
            portionMinusIv.setDebounceOnClickListener {
                portions--
                portionCountTv.text = portions.toString()
                itemClick(meal.id, ActiveMealsFragment.ACTION_MINUS, null)
            }
        }

    override fun getItemCount(): Int = activeMeals.size

    fun setActiveMeals(activeMeals: List<OrdersMealsItemUi>) {
        this.activeMeals = activeMeals
        notifyItemRangeInserted(this.activeMeals.size, activeMeals.size)
    }

}