package com.gkgio.data.meals.add

import com.gkgio.data.base.BaseService
import com.gkgio.data.exception.ServerExceptionTransformer
import com.gkgio.domain.meals.add.AddLunchItem
import com.gkgio.domain.meals.add.AddMealItem
import com.gkgio.domain.meals.add.AddMealService
import io.reactivex.Completable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.http.*
import java.io.File
import javax.inject.Inject


class AddMealServiceImpl @Inject constructor(
    private val addMealApi: AddMealApi,
    private val addMealIngredientsTransformer: AddMealIngredientsTransformer,
    serverExceptionTransformer: ServerExceptionTransformer
) : BaseService(serverExceptionTransformer), AddMealService {

    override fun uploadMeal(meal: AddMealItem): Completable =
        executeRequest(
            addMealApi.uploadMealData(
                meal.available,
                meal.calories,
                meal.cookTime,
                getTextPart("description", meal.description),
                getTextPart(
                    "ingredients",
                    addMealIngredientsTransformer.transform(meal.ingredients)
                ),
                getTextPart("name", meal.name),
                meal.portions,
                meal.price,
                meal.weight,
                getFilesPart(meal.images)
            )
        )

    override fun uploadLunch(lunch: AddLunchItem): Completable =
        executeRequest(
            addMealApi.uploadLunchData()// TODO MODELS
        )

    interface AddMealApi {
        @Multipart
        @POST("cooker/meals")
        fun uploadMealData(
            @Part("available") available: Boolean,
            @Part("calories") calories: Int,
            @Part("cook_time") cookTime: Int,
            @Part description: MultipartBody.Part,
            @Part ingredients: MultipartBody.Part,
            @Part name: MultipartBody.Part,
            @Part("portions") portions: Int,
            @Part("price") price: Int,
            @Part("weight") weight: Int,
            @Part images: List<MultipartBody.Part>
        ): Completable

        @Multipart
        @POST("cooker/lunch")
        fun uploadLunchData(): Completable
    }

    private fun getFilesPart(files: List<File?>): List<MultipartBody.Part> {
        val partList = mutableListOf<MultipartBody.Part>()
        for (file in files) {
            partList.add(
                MultipartBody.Part.createFormData(
                    "images",
                    file!!.name,
                    file.asRequestBody("image/*".toMediaTypeOrNull())
                )
            )
        }
        return partList
    }

    private fun getTextPart(name: String, value: String): MultipartBody.Part =
        MultipartBody.Part.createFormData(name, value)
}