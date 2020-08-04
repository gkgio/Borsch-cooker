package com.gkgio.borsch_cooker.di

import com.gkgio.data.own.OwnServiceImpl
import com.gkgio.domain.own.OwnService
import com.gkgio.domain.own.OwnUseCase
import com.gkgio.domain.own.OwnUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module(includes = [OwnModule.BindsModule::class])
class OwnModule {
    @Provides
    fun ownApi(retrofit: Retrofit): OwnServiceImpl.OwnApi =
        retrofit.create()

    @Module
    abstract inner class BindsModule {
        @Binds
        abstract fun ownService(arg: OwnServiceImpl): OwnService

        @Binds
        abstract fun ownUseCase(arg: OwnUseCaseImpl): OwnUseCase
    }
}