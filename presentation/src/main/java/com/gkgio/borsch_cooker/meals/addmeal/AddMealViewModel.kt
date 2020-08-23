package com.gkgio.borsch_cooker.meals.addmeal

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.meals.addmeal.edit.AddMealDataConstants
import ru.terrakok.cicerone.Router
import java.io.File
import javax.inject.Inject

class AddMealViewModel @Inject constructor(
    private val router: Router,
    screensNavigator: BaseScreensNavigator
) : BaseViewModel(screensNavigator) {

    private val imagesList = mutableListOf<File?>()
    private var ingredientsList = mutableListOf<String>()
    val onEditButton = MutableLiveData<EditData>()
    val mealImages = MutableLiveData<List<File?>>()
    val mealIngredients = MutableLiveData<List<String>>()
    val mealName = MutableLiveData<String>()
    val mealPrice = MutableLiveData<String>()
    val mealWeight = MutableLiveData<String>()
    val mealDescription = MutableLiveData<String>()

    init {
        //TODO
    }

    fun backClick() {
        router.exit()
    }

    fun onImageLoaded(image: File?) {
        image.let {
            imagesList.add(image)
            mealImages.value = imagesList
        }
    }

    fun onClickImageDelete(position: Int) {
        imagesList.removeAt(position)
        mealImages.value = imagesList
    }

    fun onClickIngredientDelete(position: Int) {
        ingredientsList.removeAt(position)
        mealIngredients.value = ingredientsList
    }

    fun onMealDataChange(dataType: String, data: String?, ingredientsList: List<String>?) {
        when (dataType) {
            AddMealDataConstants.MEAL_EDIT_TYPE_TITLE -> mealName.value = data
            AddMealDataConstants.MEAL_EDIT_TYPE_PRICE -> mealPrice.value = data
            AddMealDataConstants.MEAL_EDIT_TYPE_WEIGHT -> mealWeight.value = data
            AddMealDataConstants.MEAL_EDIT_TYPE_DESCRIPTION -> mealDescription.value = data
            AddMealDataConstants.MEAL_EDIT_TYPE_INGREDIENTS -> {
                this.ingredientsList =
                    if (ingredientsList.isNullOrEmpty()) mutableListOf() else ingredientsList.toMutableList()
                mealIngredients.value = ingredientsList
            }
        }
    }

    fun onEditButtonClick(dataType: String) {
        when (dataType) {
            AddMealDataConstants.MEAL_EDIT_TYPE_TITLE -> onEditButton.value =
                EditData(dataType, mealName.value)
            AddMealDataConstants.MEAL_EDIT_TYPE_PRICE -> onEditButton.value =
                EditData(dataType, mealPrice.value)
            AddMealDataConstants.MEAL_EDIT_TYPE_WEIGHT -> onEditButton.value =
                EditData(dataType, mealWeight.value)
            AddMealDataConstants.MEAL_EDIT_TYPE_DESCRIPTION -> onEditButton.value =
                EditData(dataType, mealDescription.value)
            AddMealDataConstants.MEAL_EDIT_TYPE_INGREDIENTS -> onEditButton.value =
                EditData(dataType, null, mealIngredients.value)
        }
    }

    fun onClickSaveMeal() {
        //TODO send to remote
    }

    data class EditData(
        val dataType: String,
        val data: String? = null,
        val ingredientsList: List<String>? = null
    )
}