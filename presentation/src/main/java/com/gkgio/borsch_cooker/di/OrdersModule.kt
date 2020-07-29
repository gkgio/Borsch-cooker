package com.gkgio.borsch_cooker.di

import com.gkgio.data.orders.OrdersServiceImpl
import com.gkgio.domain.orders.OrdersService
import com.gkgio.domain.orders.OrdersUseCase
import com.gkgio.domain.orders.OrdersUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module(includes = [OrdersModule.BindsModule::class])
class OrdersModule {
    @Provides
    fun ordersApi(retrofit: Retrofit): OrdersServiceImpl.OrdersApi =
        retrofit.create()

    @Module
    abstract inner class BindsModule {
        @Binds
        abstract fun ordersService(arg: OrdersServiceImpl): OrdersService

        @Binds
        abstract fun ordersUseCase(arg: OrdersUseCaseImpl): OrdersUseCase
    }
}