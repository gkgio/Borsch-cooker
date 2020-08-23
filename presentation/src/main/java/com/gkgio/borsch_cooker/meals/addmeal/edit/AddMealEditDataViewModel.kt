package com.gkgio.borsch_cooker.meals.addmeal.edit

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.utils.SingleLiveEvent
import javax.inject.Inject

class AddMealEditDataViewModel @Inject constructor(
    screensNavigator: BaseScreensNavigator
) : BaseViewModel(screensNavigator) {

    private var ingredientsList = mutableListOf<String>()
    val mealIngredients = MutableLiveData<List<String>>()
    val closeDialog = SingleLiveEvent<List<String>?>()
    val editInfo = MutableLiveData<EditInfo>()

    fun init(typeData: String, ingredientsList: List<String>?) {
        this.ingredientsList = if (ingredientsList.isNullOrEmpty()) mutableListOf() else ingredientsList.toMutableList()
        mealIngredients.value = ingredientsList
        when(typeData) {
            AddMealDataConstants.MEAL_EDIT_TYPE_TITLE -> editInfo.value = EditInfo("Название", null)
            AddMealDataConstants.MEAL_EDIT_TYPE_PRICE -> editInfo.value = EditInfo("Цена", "РУБЛЕЙ")
            AddMealDataConstants.MEAL_EDIT_TYPE_WEIGHT -> editInfo.value = EditInfo("Вес", "ГРАММ")
            AddMealDataConstants.MEAL_EDIT_TYPE_DESCRIPTION -> editInfo.value = EditInfo("Описание", null)
            AddMealDataConstants.MEAL_EDIT_TYPE_INGREDIENTS -> editInfo.value = EditInfo("Ингредиенты", null)
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
        val hint: String? = null
    )
}