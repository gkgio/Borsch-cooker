package com.gkgio.domain.meals.add

import io.reactivex.Completable

interface AddMealService {
    fun uploadMeal(meal: AddMealItem): Completable
    fun uploadLunch(lunch: AddLunchItem): Completable
}