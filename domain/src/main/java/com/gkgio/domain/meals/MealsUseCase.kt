package com.gkgio.domain.meals

import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface MealsUseCase {
    fun loadMeals(): Single<List<MealsItem>>
    fun loadLunches(): Single<List<LunchesItem>>
    fun incrementPortion(id: String): Completable
    fun decrementPortion(id: String): Completable
    fun setAvailabilityMeal(id: String, available: Boolean): Completable
}

class MealsUseCaseImpl @Inject constructor(
    private val mealsService: MealsService
) : MealsUseCase {
    override fun loadMeals(): Single<List<MealsItem>> =
        mealsService.loadMeals()

    override fun loadLunches(): Single<List<LunchesItem>> =
        mealsService.loadLunches()

    override fun incrementPortion(id: String): Completable =
        mealsService.incrementPortion(id)

    override fun decrementPortion(id: String): Completable =
        mealsService.decrementPortion(id)

    override fun setAvailabilityMeal(id: String, available: Boolean): Completable =
        mealsService.setAvailabilityMeal(id, available)
}