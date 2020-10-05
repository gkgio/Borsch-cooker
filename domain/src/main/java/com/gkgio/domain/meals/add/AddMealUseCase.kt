package com.gkgio.domain.meals.add

import io.reactivex.Completable
import javax.inject.Inject

interface AddMealUseCase {
    fun uploadMeal(meal: AddMealItem): Completable
    fun uploadMunch(lunch: AddMealItem): Completable //TODO
}

class AddMealUseCaseImpl @Inject constructor(
    private val addMealService: AddMealService
) : AddMealUseCase {
    override fun uploadMeal(meal: AddMealItem): Completable =
        addMealService.uploadMeal(meal)

    override fun uploadMunch(lunch: AddMealItem): Completable {
        TODO("Not yet implemented")
    }
}