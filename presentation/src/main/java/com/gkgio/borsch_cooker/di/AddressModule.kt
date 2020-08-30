package com.gkgio.borsch_cooker.di

import com.gkgio.data.address.AddressesServiceImpl
import com.gkgio.domain.address.AddressesService
import com.gkgio.domain.address.LoadAddressesUseCase
import com.gkgio.domain.address.LoadAddressesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module(includes = [AddressModule.BindsModule::class])
class AddressModule {
    @Provides
    fun addressApi(retrofit: Retrofit): AddressesServiceImpl.AddressServiceApi =
        retrofit.create()

    @Module
    abstract inner class BindsModule {
        @Binds
        abstract fun ownService(arg: AddressesServiceImpl): AddressesService

        @Binds
        abstract fun ownUseCase(arg: LoadAddressesUseCaseImpl): LoadAddressesUseCase
    }
}