package com.gkgio.data.meals

import com.gkgio.data.base.BaseService
import com.gkgio.data.exception.ServerExceptionTransformer
import com.gkgio.domain.meals.LunchesItem
import com.gkgio.domain.meals.MealsItem
import com.gkgio.domain.meals.MealsService
import io.reactivex.Single
import retrofit2.http.GET
import javax.inject.Inject

class MealsServiceImpl @Inject constructor(
    private val mealsApi: MealsApi,
    private val mealsDataResponseTransformer: MealsDataResponseTransformer,
    private val lunchesDataResponseTransformer: LunchesDataResponseTransformer,
    serverExceptionTransformer: ServerExceptionTransformer
) : BaseService(serverExceptionTransformer), MealsService {
    override fun loadMeals(): Single<List<MealsItem>> =
        executeRequest(
            mealsApi.loadMealsData().map { mealsList ->
                mealsDataResponseTransformer.transform(mealsList)
            }
        )

    override fun loadLunches(): Single<List<LunchesItem>> =
        executeRequest(
            mealsApi.loadLunchesData().map { lunchesList ->
                lunchesDataResponseTransformer.transform(lunchesList)
            }
        )

    interface MealsApi {
        @GET("cooker/meals")
        fun loadMealsData(): Single<MealsDataResponse>

        @GET("cooker/lunches")
        fun loadLunchesData(): Single<LunchesDataResponse>
    }

}