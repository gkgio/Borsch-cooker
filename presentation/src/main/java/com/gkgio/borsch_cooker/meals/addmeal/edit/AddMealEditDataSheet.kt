package com.gkgio.borsch_cooker.meals.addmeal.edit

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.bottomsheet.BaseBottomSheetDialog
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel
import com.gkgio.borsch_cooker.ext.observeValue
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.borsch_cooker.meals.addmeal.AddMealIngredientsAdapter
import com.gkgio.borsch_cooker.utils.FragmentArgumentDelegate
import kotlinx.android.synthetic.main.layout_add_meal_sheet.*

class AddMealEditDataSheet : BaseBottomSheetDialog() {

    private lateinit var viewModel: AddMealEditDataViewModel
    private lateinit var mealDataChangeListener: MealDataChangeListener
    private lateinit var mealIngredientsAdapter: AddMealIngredientsAdapter

    companion object {
        fun getInstance(typeData: String, currentData: String?, ingredientsList: List<String>?) =
            AddMealEditDataSheet().apply {
                this.currentData = if (currentData.isNullOrEmpty()) "" else currentData
                this.ingredientsList =
                    if (ingredientsList.isNullOrEmpty()) listOf() else ingredientsList
                this.typeData = typeData
            }
    }

    private var currentData: String by FragmentArgumentDelegate()
    private var typeData: String by FragmentArgumentDelegate()
    private var ingredientsList: List<String> by FragmentArgumentDelegate()

    override fun getLayoutId(): Int = R.layout.layout_add_meal_sheet

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mealDataChangeListener = when {
            parentFragment is MealDataChangeListener -> parentFragment as MealDataChangeListener
            context is MealDataChangeListener -> context
            else -> throw IllegalStateException("Either parentFragment or context must implement MealDataChangeListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = createViewModel { AppInjector.appComponent.addMealEditDataViewModel }
    }

    override fun setupView(view: View) = with(view) {
        super.setupView(view)
        viewModel.init(typeData, ingredientsList)
        //TODO
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initIngredientsRv()
        addMealSheetData.setText(currentData)
        addMealSaveTv.setDebounceOnClickListener {
            viewModel.onSaveClick()
        }
        addMealAddNewIngredientTv.setDebounceOnClickListener {
            viewModel.onClickIngredientAdd(addMealSheetData.text.toString())
        }
        if (typeData == AddMealDataConstants.MEAL_EDIT_TYPE_PRICE ||
            typeData == AddMealDataConstants.MEAL_EDIT_TYPE_WEIGHT
        ) {
            addMealSheetData.inputType = InputType.TYPE_CLASS_NUMBER
        } else if (typeData == AddMealDataConstants.MEAL_EDIT_TYPE_INGREDIENTS) {
            addMealAddNewIngredientTv.isVisible = true
            addMealEditIngredientsRv.isVisible = true
        }
        viewModel.closeDialog.observeValue(this) {
            mealDataChangeListener.onMealDataChange(typeData, addMealSheetData.text.toString(), it)
            dismiss()
        }
        viewModel.editInfo.observeValue(this) {
            addMealSheetTitle.text = it.title
            addMealSheetValue.isVisible = !it.hint.equals(null)
            addMealSheetValue.text = it.hint
        }
        viewModel.mealIngredients.observeValue(this) {
            mealIngredientsAdapter.setIngredientsList(it)
        }
    }

    private fun initIngredientsRv() {
        mealIngredientsAdapter = AddMealIngredientsAdapter {
            viewModel.onClickIngredientDelete(it)
        }
        addMealEditIngredientsRv.adapter = mealIngredientsAdapter
        addMealEditIngredientsRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    internal interface MealDataChangeListener {
        fun onMealDataChange(dataType: String, data: String, ingredientsList: List<String>?)
    }

}