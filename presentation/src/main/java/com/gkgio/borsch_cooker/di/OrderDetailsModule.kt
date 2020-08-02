package com.gkgio.borsch_cooker.di

import com.gkgio.data.orderdetails.OrderDetailsServiceImpl
import com.gkgio.domain.orderdetails.OrderDetailsService
import com.gkgio.domain.orderdetails.OrderDetailsUseCase
import com.gkgio.domain.orderdetails.OrderDetailsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module(includes = [OrderDetailsModule.BindsModule::class])
class OrderDetailsModule {
    @Provides
    fun orderDetailsApi(retrofit: Retrofit): OrderDetailsServiceImpl.OrderDetailsApi =
        retrofit.create()

    @Module
    abstract inner class BindsModule {
        @Binds
        abstract fun orderDetailsService(arg: OrderDetailsServiceImpl): OrderDetailsService

        @Binds
        abstract fun orderDetailsUseCase(arg: OrderDetailsUseCaseImpl): OrderDetailsUseCase
    }
}