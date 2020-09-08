package com.gkgio.borsch_cooker.profile.name

import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.ext.applySchedulers
import com.gkgio.borsch_cooker.utils.SingleLiveEvent
import com.gkgio.borsch_cooker.utils.events.UserProfileChanged
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.domain.auth.AuthUseCase
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class InputNameViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    private val authUseCase: AuthUseCase,
    private val userProfileChanged: UserProfileChanged,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val progressState = SingleLiveEvent<Boolean>()

    fun onSaveNameClicked(name: String, secondName: String) {
        authUseCase
            .saveName(name, secondName)
            .applySchedulers()
            .doOnSubscribe { progressState.value = true }
            .subscribe({
                progressState.value = false
                userProfileChanged.onComplete("")
                router.exit()
            }, {
                progressState.value = false
                processThrowable(it)
            }).addDisposable()
    }
}