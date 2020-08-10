package com.gkgio.borsch_cooker.onboarding

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.ext.applySchedulers
import com.gkgio.borsch_cooker.ext.isNonInitialized
import com.gkgio.borsch_cooker.ext.nonNullValue
import com.gkgio.borsch_cooker.ext.notIsNullOrBlank
import com.gkgio.borsch_cooker.navigation.Screens
import com.gkgio.borsch_cooker.utils.events.UserProfileChanged
import com.gkgio.domain.auth.AuthUseCase
import com.gkgio.domain.auth.Cooker
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class OnboardingViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    private val authUseCase: AuthUseCase,
    private val userProfileChanged: UserProfileChanged,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()

    init {
        if (state.isNonInitialized()) {
            state.value = State()
            updateScreen()

            userProfileChanged
                .getEventResult()
                .applySchedulers()
                .subscribe({
                    updateScreen()
                }, {
                    // do not use
                }).addDisposable()
        }
    }

    fun onBtnActionClicked() {
        state.nonNullValue.let {
            if (!it.hasPhone) {
                router.navigateTo(Screens.InputPhoneFragmentScreen(true))
                return
            }
            if (!it.hasName) {
                router.navigateTo(Screens.InputNameFragmentScreen)
                return
            }

            if (!it.hasAvatar && !it.avatarInputSkipped) {
                router.navigateTo(Screens.InputAvatarFragmentScreen)
                return
            }
        }
    }

    fun onBtnSkipClicked() {
        state.value = state.nonNullValue.copy(avatarInputSkipped = true)
    }

    private fun updateScreen() {
        val profile = authUseCase.loadUserProfile()
        state.value = state.nonNullValue.copy(cooker = profile)
        profile?.let {
            val phone = it.phone
            val name = it.firstName
            val avatar = it.avatarUrl

            if (phone.notIsNullOrBlank() &&
                name.notIsNullOrBlank() &&
                (avatar.notIsNullOrBlank() || state.nonNullValue.avatarInputSkipped)
            ) {
                router.newRootScreen(Screens.MainFragmentScreen)
                return
            }

            state.value = state.nonNullValue.copy(
                hasPhone = phone.notIsNullOrBlank(),
                phone = if (phone.notIsNullOrBlank()) {
                    phone
                } else {
                    null
                }
            )

            state.value = state.nonNullValue.copy(
                hasName = name.notIsNullOrBlank(),
                nameWithSecondName = if (name.notIsNullOrBlank()) {
                    name + " " + it.lastName
                } else {
                    null
                }
            )

            state.value = state.nonNullValue.copy(hasAvatar = avatar.notIsNullOrBlank())
        }
    }

    data class State(
        val cooker: Cooker? = null,
        val hasPhone: Boolean = false,
        val hasName: Boolean = false,
        val hasAvatar: Boolean = false,
        val hasAddress: Boolean = false,
        val nameWithSecondName: String? = null,
        val phone: String? = null,
        val avatarInputSkipped: Boolean = false
    )
}