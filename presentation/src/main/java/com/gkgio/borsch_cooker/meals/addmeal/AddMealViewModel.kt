package com.gkgio.borsch_cooker.meals.addmeal

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.ext.applySchedulers
import com.gkgio.borsch_cooker.ext.isNonInitialized
import com.gkgio.borsch_cooker.ext.nonNullValue
import com.gkgio.borsch_cooker.meals.addmeal.edit.AddMealDataConstants
import com.gkgio.borsch_cooker.utils.SingleLiveEvent
import com.gkgio.borsch_cooker.utils.events.MealsListChanged
import com.gkgio.domain.meals.add.AddMealItem
import com.gkgio.domain.meals.add.AddMealUseCase
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class AddMealViewModel @Inject constructor(
    private val addMealUseCase: AddMealUseCase,
    private val mealsListChanged: MealsListChanged,
    screensNavigator: BaseScreensNavigator
) : BaseViewModel(screensNavigator) {

    private val imagesList = mutableListOf<File?>()
    private var ingredientsList = mutableListOf<String>()
    val onEditButton = SingleLiveEvent<EditData>()
    val mealImages = MutableLiveData<List<File?>>()
    val mealIngredients = MutableLiveData<List<String>>()
    val mealName = MutableLiveData<String>()
    val mealPrice = MutableLiveData<String>()
    val mealWeight = MutableLiveData<String>()
    val mealDescription = MutableLiveData<String>()
    val mealCalories = MutableLiveData<String>()
    val mealCookTime = MutableLiveData<String>()
    val state = MutableLiveData<State>()

    init {
        if (state.isNonInitialized()) {
            state.value = State(isInitialError = false, loaded = false, isHasEmptyField = false)
        }
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
            AddMealDataConstants.MEAL_EDIT_TYPE_CALORIES -> mealCalories.value = data
            AddMealDataConstants.MEAL_EDIT_TYPE_COOK_TIME -> mealCookTime.value = data
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
            AddMealDataConstants.MEAL_EDIT_TYPE_CALORIES -> onEditButton.value =
                EditData(dataType, mealCalories.value)
            AddMealDataConstants.MEAL_EDIT_TYPE_COOK_TIME -> onEditButton.value =
                EditData(dataType, mealCookTime.value)
            AddMealDataConstants.MEAL_EDIT_TYPE_INGREDIENTS -> onEditButton.value =
                EditData(dataType, null, mealIngredients.value)
        }
    }

    fun onClickSaveMeal() {
        if (mealCalories.value.isNullOrEmpty() ||
            mealCookTime.value.isNullOrEmpty() ||
            mealDescription.value.isNullOrEmpty() ||
            mealIngredients.value.isNullOrEmpty() ||
            mealName.value.isNullOrEmpty() ||
            mealPrice.value.isNullOrEmpty() ||
            mealWeight.value.isNullOrEmpty() ||
            mealImages.value.isNullOrEmpty()
        ) {
            state.value = state.nonNullValue.copy(isHasEmptyField = true)
        } else {
            addMealUseCase
                .uploadMeal(
                    AddMealItem(
                        true,
                        mealCalories.value!!.toInt(),
                        mealCookTime.value!!.toInt(),
                        mealDescription.value!!,
                        mealIngredients.value!!,
                        mealName.value!!,
                        1,
                        mealPrice.value!!.toInt(),
                        mealWeight.value!!.toInt(),
                        mealImages.value!!
                    )
                )
                .applySchedulers()
                .subscribe(
                    {
                        state.value = state.nonNullValue.copy(
                            loaded = true,
                            isInitialError = false,
                            isHasEmptyField = false
                        )
                        mealsListChanged.onComplete("")
                    },
                    { throwable ->
                        Timber.e(throwable, "Error upload meal")
                        processThrowable(throwable)
                        state.value =
                            state.nonNullValue.copy(loaded = false, isInitialError = true)
                    }
                )
                .addDisposable()
        }
    }

    data class EditData(
        val dataType: String,
        val data: String? = null,
        val ingredientsList: List<String>? = null
    )

    data class State(
        val loaded: Boolean,
        val isInitialError: Boolean,
        val isHasEmptyField: Boolean
    )
}