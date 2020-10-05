package com.gkgio.borsch_cooker.meals.addlunch

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.ext.applySchedulers
import com.gkgio.borsch_cooker.ext.isNonInitialized
import com.gkgio.borsch_cooker.ext.nonNullValue
import com.gkgio.borsch_cooker.meals.MealsItemUi
import com.gkgio.borsch_cooker.meals.addmeal.edit.AddMealDataConstants
import com.gkgio.borsch_cooker.navigation.Screens
import com.gkgio.borsch_cooker.utils.SingleLiveEvent
import com.gkgio.borsch_cooker.utils.events.LunchMealsChanged
import com.gkgio.borsch_cooker.utils.events.LunchesListChanged
import com.gkgio.domain.meals.add.AddMealUseCase
import pl.aprilapps.easyphotopicker.MediaFile
import ru.terrakok.cicerone.Router
import java.io.File
import javax.inject.Inject

class AddLunchViewModel @Inject constructor(
    screensNavigator: BaseScreensNavigator,
    private val addMealsUseCase: AddMealUseCase,
    private val lunchMealsChanged: LunchMealsChanged,
    private val lunchesListChanged: LunchesListChanged,
    private val router: Router
) : BaseViewModel(screensNavigator) {

    val lunchImages = MutableLiveData<List<File?>>()
    val lunchName = MutableLiveData<String>()
    val lunchPrice = MutableLiveData<String>()
    val onEditButton = SingleLiveEvent<EditData>()
    val mealsList = MutableLiveData<List<MealsItemUi>>()
    private val imagesList = mutableListOf<File?>()
    val state = SingleLiveEvent<State>()
    val errorLoadPhoto = SingleLiveEvent<Unit>()

    init {
        state.value = State(
            isInitialError = false,
            loaded = false,
            isHasEmptyField = false
        )
        initMealsChanges()
    }

    fun onAddMealsClick() {
        router.navigateTo(Screens.SelectMealsScreen())
    }

    fun onImageLoaded(imageFiles: Array<MediaFile>) {
        if (imageFiles.isNotEmpty()) {
            val image = imageFiles[0].file
            image.let {
                imagesList.add(image)
                lunchImages.value = imagesList
            }
        }
    }

    fun onClickImageDelete(position: Int) {
        imagesList.removeAt(position)
        lunchImages.value = imagesList
    }

    fun onEditButtonClick(dataType: String) {
        when (dataType) {
            AddMealDataConstants.MEAL_EDIT_TYPE_TITLE -> onEditButton.value =
                EditData(dataType, lunchName.value)
            AddMealDataConstants.LUNCH_EDIT_TYPE_PRICE -> onEditButton.value =
                EditData(dataType, lunchPrice.value)
        }
    }

    fun onDeleteButtonClick(id: String) {
        val newMealsList = mealsList.value?.toMutableList()
        newMealsList?.remove(newMealsList.find { it.id == id })
        mealsList.value = newMealsList
        lunchPrice.value = calculateLunchPrice(newMealsList!!.toList())
    }

    fun onLunchDataChange(dataType: String, data: String?) {
        when (dataType) {
            AddMealDataConstants.MEAL_EDIT_TYPE_TITLE -> lunchName.value = data
            AddMealDataConstants.LUNCH_EDIT_TYPE_PRICE -> lunchPrice.value = data
        }
    }

    fun onClickSaveLunch() {
        if (lunchName.value.isNullOrEmpty() || lunchPrice.value.isNullOrEmpty() || mealsList.value.isNullOrEmpty() || lunchImages.value.isNullOrEmpty()) {
            state.value = state.nonNullValue.copy(isHasEmptyField = true)
        } else {
            //addMealsUseCase.uploadMunch() TODO UPLOAD TO SERVER
            //TODO subscribe lunchesListChanged.onComplete("")
        }
    }

    private fun initMealsChanges() {
        lunchMealsChanged
            .getEventResult()
            .applySchedulers()
            .subscribe {
                mealsList.value = it
                lunchPrice.value = calculateLunchPrice(it)
            }
            .addDisposable()
    }

    private fun calculateLunchPrice(meals: List<MealsItemUi>): String {
        var newPrice = 0
        for (meal in meals) {
            newPrice += meal.price
        }
        return newPrice.toString()
    }

    fun onErrorLoadFile() {
        errorLoadPhoto.call()
    }

    data class EditData(
        val dataType: String,
        val data: String? = null
    )

    data class State(
        val loaded: Boolean,
        val isInitialError: Boolean,
        val isHasEmptyField: Boolean
    )
}