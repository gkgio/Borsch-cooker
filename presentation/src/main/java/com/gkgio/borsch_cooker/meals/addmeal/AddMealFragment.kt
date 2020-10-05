package com.gkgio.borsch_cooker.meals.addmeal

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.*
import com.gkgio.borsch_cooker.meals.addmeal.edit.AddMealDataConstants
import com.gkgio.borsch_cooker.meals.addmeal.edit.AddMealEditDataSheet
import com.gkgio.borsch_cooker.utils.DialogUtils
import kotlinx.android.synthetic.main.fragment_meals_add.*
import pl.aprilapps.easyphotopicker.*

class AddMealFragment : BaseFragment<AddMealViewModel>(),
    AddMealEditDataSheet.MealDataChangeListener {

    private lateinit var mealImagesAdapter: AddMealImagesAdapter
    private lateinit var mealIngredientsAdapter: AddMealIngredientsAdapter
    private lateinit var easyImageMeals: EasyImage

    companion object {
        val TAG = AddMealFragment::class.java.simpleName
    }

    override fun getLayoutId(): Int = R.layout.fragment_meals_add

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.addMealViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setLeftIconClickListener {
            onBackClick()
        }
        initMealImagesRv()
        initIngredientsRv()
        viewModel.state.observeValue(this) {
            when {
                it.isInitialError -> {
                    showError(getString(R.string.error_empty_text))
                }
                it.isHasEmptyField -> {
                    showError(getString(R.string.error_empty_filed))
                }
                it.loaded -> {
                    onBackClick()
                }
            }
        }
        viewModel.mealImages.observeValue(this) {
            mealImagesAdapter.setImagesList(it)
        }
        viewModel.mealIngredients.observeValue(this) {
            mealIngredientsAdapter.setIngredientsList(it)
        }
        viewModel.mealName.observeValue(this) {
            addMealName.text = it
        }
        viewModel.mealPrice.observeValue(this) {
            addMealPrice.text = getString(R.string.meals_edit_price, it)
        }
        viewModel.mealWeight.observeValue(this) {
            addMealWeight.text = getString(R.string.meals_edit_weight, it)
        }
        viewModel.mealDescription.observeValue(this) {
            addMealDescription.text = it
        }
        viewModel.mealCalories.observeValue(this) {
            addMealCalories.text = getString(R.string.meals_edit_calories, it)
        }
        viewModel.mealCookTime.observeValue(this) {
            addMealCookTime.text = getString(R.string.meals_edit_cook_time, it)
        }
        viewModel.onEditButton.observeValue(this) {
            showDialog(
                AddMealEditDataSheet.getInstance(
                    it.dataType,
                    it.data,
                    it.ingredientsList
                ), TAG
            )
        }
        easyImageMeals = EasyImage.Builder(requireContext())
            .setChooserTitle(getString(R.string.file_choose_photo))
            .setCopyImagesToPublicGalleryFolder(true)
            .setChooserType(ChooserType.CAMERA_AND_GALLERY)
            .allowMultiple(false)
            .build()
        addMealImagesLl.setDebounceOnClickListener {
            clickOnAddPhoto()
        }
        addMealSaveButton.setDebounceOnClickListener {
            viewModel.onClickSaveMeal()
        }
        addMealEditPriceTv.setDebounceOnClickListener {
            viewModel.onEditButtonClick(AddMealDataConstants.MEAL_EDIT_TYPE_PRICE)
        }
        addMealEditWeightTv.setDebounceOnClickListener {
            viewModel.onEditButtonClick(AddMealDataConstants.MEAL_EDIT_TYPE_WEIGHT)
        }
        addMealEditTitleTv.setDebounceOnClickListener {
            viewModel.onEditButtonClick(AddMealDataConstants.MEAL_EDIT_TYPE_TITLE)
        }
        addMealEditDescriptionTv.setDebounceOnClickListener {
            viewModel.onEditButtonClick(AddMealDataConstants.MEAL_EDIT_TYPE_DESCRIPTION)
        }
        addMealAddIngredientsTv.setDebounceOnClickListener {
            viewModel.onEditButtonClick(AddMealDataConstants.MEAL_EDIT_TYPE_INGREDIENTS)
        }
        addMealEditCaloriesTv.setDebounceOnClickListener {
            viewModel.onEditButtonClick(AddMealDataConstants.MEAL_EDIT_TYPE_CALORIES)
        }
        addMealEditCookTimeTv.setDebounceOnClickListener {
            viewModel.onEditButtonClick(AddMealDataConstants.MEAL_EDIT_TYPE_COOK_TIME)
        }
    }

    override fun onMealDataChange(dataType: String, data: String, ingredientsList: List<String>?) {
        viewModel.onMealDataChange(dataType, data, ingredientsList)
    }

    private fun initMealImagesRv() {
        mealImagesAdapter = AddMealImagesAdapter {
            viewModel.onClickImageDelete(it)
        }
        addMealImagesRv.adapter = mealImagesAdapter
        addMealImagesRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun initIngredientsRv() {
        mealIngredientsAdapter = AddMealIngredientsAdapter {
            viewModel.onClickIngredientDelete(it)
        }
        addMealIngredientsRv.adapter = mealIngredientsAdapter
        addMealIngredientsRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun clickOnAddPhoto() {
        if (requireContext().checkWriteStorageGranted()) {
            openGallery()
        } else {
            requestWriteStoragePermission()
                .subscribe {
                    openGallery()
                }.addDisposable()
        }
    }

    private fun openGallery() {
        easyImageMeals.openGallery(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val unmaskedRequestCode = requestCode.and(0x0000ffff)

        easyImageMeals.handleActivityResult(
            unmaskedRequestCode,
            resultCode,
            data,
            requireActivity(),
            object : DefaultCallback() {

                override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                    if (imageFiles.isNotEmpty()) {
                        viewModel.onImageLoaded(imageFiles[0].file)
                    }
                }

                override fun onImagePickerError(error: Throwable, source: MediaSource) {
                    DialogUtils.showError(
                        view,
                        getString(R.string.file_selection_failed)
                    )
                }

                override fun onCanceled(source: MediaSource) {

                }
            })
    }
}