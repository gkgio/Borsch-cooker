package com.gkgio.data.meals

import com.gkgio.data.base.BaseService
import com.gkgio.data.exception.ServerExceptionTransformer
import com.gkgio.domain.meals.LunchesItem
import com.gkgio.domain.meals.MealsItem
import com.gkgio.domain.meals.MealsService
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*
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

    override fun incrementPortion(id: String): Completable =
        executeRequest(
            mealsApi.incrementPortion(id)
        )

    override fun decrementPortion(id: String): Completable =
        executeRequest(
            mealsApi.decrementPortion(id)
        )

    override fun setAvailabilityMeal(id: String, available: Boolean): Completable =
        executeRequest(
            mealsApi.setAvailabilityMeal(id, available)
        )

    interface MealsApi {
        @GET("cooker/meals")
        fun loadMealsData(): Single<MealsDataResponse>

        @GET("cooker/lunches")
        fun loadLunchesData(): Single<LunchesDataResponse>

        @POST("cooker/meals/increment/{id}")
        fun incrementPortion(@Path("id") id: String): Completable

        @POST("cooker/meals/decrement/{id}")
        fun decrementPortion(@Path("id") id: String): Completable

        @Multipart
        @POST("cooker/meals/set_available/{id}")
        fun setAvailabilityMeal(
            @Path("id") id: String,
            @Part("available") available: Boolean
        ): Completable
    }

}