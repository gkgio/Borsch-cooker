package com.gkgio.borsch_cooker.di

import com.gkgio.data.profile.ProfileServiceImpl
import com.gkgio.domain.profile.LoadProfileUseCase
import com.gkgio.domain.profile.LoadProfileUseCaseImpl
import com.gkgio.domain.profile.ProfileService
import dagger.Module
import dagger.Provides
import dagger.Binds
import retrofit2.Retrofit
import retrofit2.create

@Module(includes = [ProfileModule.BindsModule::class])
class ProfileModule {
    @Provides
    fun serviceApi(retrofit: Retrofit): ProfileServiceImpl.ProfileApi =
        retrofit.create()

    @Module
    abstract inner class BindsModule {

        @Binds
        abstract fun bindProfileService(arg: ProfileServiceImpl): ProfileService

        @Binds
        abstract fun bindProfileUseCase(arg: LoadProfileUseCaseImpl): LoadProfileUseCase
    }
}