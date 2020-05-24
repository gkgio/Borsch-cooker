package com.gkgio.borsch_cooker.di


import com.gkgio.data.support.SupportServiceImpl
import com.gkgio.domain.support.SupportService
import com.gkgio.domain.support.SupportUseCase
import com.gkgio.domain.support.SupportUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.Binds
import retrofit2.Retrofit
import retrofit2.create

@Module(includes = [SupportModule.BindsModule::class])
class SupportModule {
    @Provides
    fun chatServiceApi(retrofit: Retrofit): SupportServiceImpl.SupportServiceApi =
        retrofit.create()

    @Module
    abstract inner class BindsModule {

        @Binds
        abstract fun supportService(arg: SupportServiceImpl): SupportService

        @Binds
        abstract fun supportUseCase(arg: SupportUseCaseImpl): SupportUseCase
    }
}