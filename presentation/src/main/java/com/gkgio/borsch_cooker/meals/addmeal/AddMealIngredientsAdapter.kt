package com.gkgio.borsch_cooker.meals.addmeal

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.borsch_cooker.view.SyntheticViewHolder
import kotlinx.android.synthetic.main.layout_add_meal_ingredient_view_holder.view.*

class AddMealIngredientsAdapter(
    val itemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<SyntheticViewHolder>() {

    private var ingredientsList = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder {
        return SyntheticViewHolder.inflateFrom(
            parent,
            R.layout.layout_add_meal_ingredient_view_holder
        )
    }

    override fun getItemCount(): Int = ingredientsList.size

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) =
        with(holder.itemView) {
            addMealIngredientTitle.text = ingredientsList[position]
            addMealDeleteIngredient.setDebounceOnClickListener {
                itemClick(position)
            }
        }

    fun setIngredientsList(ingredientsList: List<String>) {
        this.ingredientsList = ingredientsList
        notifyDataSetChanged()
    }

}