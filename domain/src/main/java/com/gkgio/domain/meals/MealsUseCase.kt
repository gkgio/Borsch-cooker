package com.gkgio.domain.meals

import io.reactivex.Single
import javax.inject.Inject

interface MealsUseCase {
    fun loadMeals(): Single<List<MealsItem>>
    fun loadLunches(): Single<List<LunchesItem>>
}

class MealsUseCaseImpl @Inject constructor(
    private val mealsService: MealsService
) : MealsUseCase {
    override fun loadMeals(): Single<List<MealsItem>> =
        mealsService.loadMeals()

    override fun loadLunches(): Single<List<LunchesItem>> =
        mealsService.loadLunches()
}