package com.gkgio.borsch_cooker.meals.addlunch

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.*
import com.gkgio.borsch_cooker.meals.MealsItemUi
import com.gkgio.borsch_cooker.meals.addmeal.AddMealImagesAdapter
import com.gkgio.borsch_cooker.meals.addmeal.edit.AddMealDataConstants
import com.gkgio.borsch_cooker.meals.addmeal.edit.AddMealEditDataSheet
import com.gkgio.borsch_cooker.utils.DialogUtils
import kotlinx.android.synthetic.main.fragment_lunches_add.*
import pl.aprilapps.easyphotopicker.*

class AddLunchFragment : BaseFragment<AddLunchViewModel>(),
    AddMealEditDataSheet.MealDataChangeListener {

    private lateinit var lunchImagesAdapter: AddMealImagesAdapter
    private lateinit var mealsAdapter: AddLunchMealsAdapter
    private lateinit var easyImageMeals: EasyImage

    companion object {
        val TAG = AddLunchFragment::class.java.simpleName
    }

    override fun getLayoutId(): Int = R.layout.fragment_lunches_add

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.addLunchViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLunchImagesRv()
        initMealsForLunchRv()
        easyImageMeals = EasyImage.Builder(requireContext())
            .setChooserTitle(getString(R.string.file_choose_photo))
            .setCopyImagesToPublicGalleryFolder(true)
            .setChooserType(ChooserType.CAMERA_AND_GALLERY)
            .allowMultiple(false)
            .build()
        addMealsCv.setDebounceOnClickListener {
            viewModel.onAddMealsClick()
        }
        addLunchImagesLl.setDebounceOnClickListener {
            clickOnAddPhoto()
        }
        toolbar.setLeftIconClickListener {
            onBackClick()
        }
        addLunchEditTitleTv.setDebounceOnClickListener {
            viewModel.onEditButtonClick(AddMealDataConstants.MEAL_EDIT_TYPE_TITLE)
        }
        addLunchEditPriceTv.setDebounceOnClickListener {
            viewModel.onEditButtonClick(AddMealDataConstants.LUNCH_EDIT_TYPE_PRICE)
        }
        viewModel.lunchImages.observeValue(this) {
            lunchImagesAdapter.setImagesList(it)
        }
        viewModel.onEditButton.observeValue(this) {
            showDialog(
                AddMealEditDataSheet.getInstance(it.dataType, it.data, null),
                TAG
            )
        }
        viewModel.lunchName.observeValue(this) {
            addLunchNameTv.text = it
        }
        viewModel.lunchPrice.observeValue(this) {
            addLunchPriceTv.text = getString(R.string.orders_sum, it)
        }
        viewModel.mealsList.observeValue(this) {
            mealsAdapter.setMealsList(it)
        }
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
        addLunchSaveButton.setDebounceOnClickListener {
            viewModel.onClickSaveLunch()
        }
    }

    private fun initLunchImagesRv() {
        lunchImagesAdapter = AddMealImagesAdapter {
            viewModel.onClickImageDelete(it)
        }
        addLunchImagesRv.adapter = lunchImagesAdapter
        addLunchImagesRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun initMealsForLunchRv() {
        mealsAdapter = AddLunchMealsAdapter {
            viewModel.onDeleteButtonClick(it)
        }
        addLunchMealsRv.adapter = mealsAdapter
        addLunchMealsRv.layoutManager = LinearLayoutManager(requireContext())
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

    override fun onMealDataChange(dataType: String, data: String, ingredientsList: List<String>?) {
        viewModel.onLunchDataChange(dataType, data)
    }

}