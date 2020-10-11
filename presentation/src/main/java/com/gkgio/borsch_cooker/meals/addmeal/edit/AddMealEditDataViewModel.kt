package com.gkgio.borsch_cooker.meals.addmeal.edit

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.utils.SingleLiveEvent
import javax.inject.Inject

class AddMealEditDataViewModel @Inject constructor(
    screensNavigator: BaseScreensNavigator,
    private val context: Context
) : BaseViewModel(screensNavigator) {

    private var ingredientsList = mutableListOf<String>()
    val mealIngredients = MutableLiveData<List<String>>()
    val closeDialog = SingleLiveEvent<List<String>?>()
    val editInfo = MutableLiveData<EditInfo>()

    fun init(typeData: String, ingredientsList: List<String>?) {
        this.ingredientsList =
            if (ingredientsList.isNullOrEmpty()) mutableListOf() else ingredientsList.toMutableList()
        mealIngredients.value = ingredientsList
        when (typeData) {
            AddMealDataConstants.MEAL_EDIT_TYPE_TITLE -> editInfo.value =
                EditInfo(
                    context.getString(R.string.meals_add_name),
                    null,
                    context.getString(R.string.meals_add_hints_name)
                )
            AddMealDataConstants.MEAL_EDIT_TYPE_PRICE -> editInfo.value =
                EditInfo(
                    context.getString(R.string.meals_add_price),
                    context.getString(R.string.meals_edit_price_value),
                    context.getString(R.string.meals_add_hints_price)
                )
            AddMealDataConstants.LUNCH_EDIT_TYPE_PRICE -> editInfo.value =
                EditInfo(
                    context.getString(R.string.meals_add_price),
                    null, context.getString(R.string.meals_add_hints_price)
                )
            AddMealDataConstants.MEAL_EDIT_TYPE_WEIGHT -> editInfo.value =
                EditInfo(
                    context.getString(R.string.meals_add_weight),
                    context.getString(R.string.meals_edit_weight_value),
                    context.getString(R.string.meals_add_hints_weight)
                )
            AddMealDataConstants.MEAL_EDIT_TYPE_CALORIES -> editInfo.value =
                EditInfo(
                    context.getString(R.string.meals_add_calories),
                    context.getString(R.string.meals_edit_calories_value),
                    context.getString(R.string.meals_add_hints_calories)
                )
            AddMealDataConstants.MEAL_EDIT_TYPE_COOK_TIME -> editInfo.value =
                EditInfo(
                    context.getString(R.string.meals_add_cook_time),
                    context.getString(R.string.meals_edit_cook_time_value),
                    context.getString(R.string.meals_add_hints_cook_time)
                )
            AddMealDataConstants.MEAL_EDIT_TYPE_DESCRIPTION -> editInfo.value =
                EditInfo(
                    context.getString(R.string.meals_add_description),
                    null,
                    context.getString(R.string.meals_add_hints_description)
                )
            AddMealDataConstants.MEAL_EDIT_TYPE_INGREDIENTS -> editInfo.value =
                EditInfo(
                    context.getString(R.string.meals_add_ingredients),
                    null
                )
        }
    }

    fun onSaveClick() {
        if (true) { //TODO проверка на валидность данных
            closeDialog.value = mealIngredients.value
        }
    }

    fun onClickIngredientDelete(position: Int) {
        ingredientsList.removeAt(position)
        mealIngredients.value = ingredientsList
    }

    fun onClickIngredientAdd(ingredient: String) {
        ingredientsList.add(ingredient)
        mealIngredients.value = ingredientsList
    }

    data class EditInfo(
        val title: String,
        val hint: String? = null,
        val hintEditText: String? = null
    )
}