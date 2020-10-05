package com.gkgio.data.meals.add

import com.gkgio.data.BaseTransformer
import javax.inject.Inject

class AddMealIngredientsTransformer @Inject constructor() : BaseTransformer<List<String>, String> {
    override fun transform(data: List<String>): String {
        var ingredients = ""
        for (ingredient in data) {
            ingredients += "$ingredient, "
        }
        return ingredients
    }

}