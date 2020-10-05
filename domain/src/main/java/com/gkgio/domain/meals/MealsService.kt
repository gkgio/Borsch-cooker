package com.gkgio.domain.meals

import io.reactivex.Single

interface MealsService {
    fun loadMeals(): Single<List<MealsItem>>
    fun loadLunches(): Single<List<LunchesItem>>
}