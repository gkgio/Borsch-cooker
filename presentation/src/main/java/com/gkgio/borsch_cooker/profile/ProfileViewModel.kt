package com.gkgio.borsch_cooker.profile

import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.utils.events.UserProfileChanged
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.domain.auth.AuthUseCase
import com.gkgio.domain.profile.LoadProfileUseCase
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    private val authUseCase: AuthUseCase,
    private val userProfileChanged: UserProfileChanged,
    private val loadProfileUseCase: LoadProfileUseCase,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    init {
        loadProfile()
    }

    private fun loadProfile() {
        loadProfileUseCase
            .loadProfile()
            .doOnSubscribe { }
            .subscribe({

            }, {

            }).addDisposable()
    }

    data class State(
        val avatarUrl: String
    )
}