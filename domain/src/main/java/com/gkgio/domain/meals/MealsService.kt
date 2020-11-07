package com.gkgio.domain.meals

import io.reactivex.Completable
import io.reactivex.Single

interface MealsService {
    fun loadMeals(): Single<List<MealsItem>>
    fun loadLunches(): Single<List<LunchesItem>>
    fun incrementPortion(id: String): Completable
    fun decrementPortion(id: String): Completable
    fun setAvailabilityMeal(id: String, available: Boolean): Completable
}