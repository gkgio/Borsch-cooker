package com.gkgio.borsch_cooker.di

import com.gkgio.data.meals.MealsServiceImpl
import com.gkgio.data.meals.add.AddMealServiceImpl
import com.gkgio.domain.meals.MealsService
import com.gkgio.domain.meals.MealsUseCase
import com.gkgio.domain.meals.MealsUseCaseImpl
import com.gkgio.domain.meals.add.AddMealService
import com.gkgio.domain.meals.add.AddMealUseCase
import com.gkgio.domain.meals.add.AddMealUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module(includes = [MealsModule.BindsModule::class])
class MealsModule {
    @Provides
    fun mealsApi(retrofit: Retrofit): MealsServiceImpl.MealsApi =
        retrofit.create()

    @Provides
    fun addMealApi(retrofit: Retrofit): AddMealServiceImpl.AddMealApi =
        retrofit.create()

    @Module
    abstract inner class BindsModule {
        @Binds
        abstract fun mealsService(arg: MealsServiceImpl): MealsService

        @Binds
        abstract fun mealsUseCase(arg: MealsUseCaseImpl): MealsUseCase

        @Binds
        abstract fun addMealService(arg: AddMealServiceImpl): AddMealService

        @Binds
        abstract fun addMealUseCase(arg: AddMealUseCaseImpl): AddMealUseCase
    }
}