package com.gkgio.borsch_cooker.profile

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.ext.applySchedulers
import com.gkgio.borsch_cooker.ext.nonNullValue
import com.gkgio.borsch_cooker.navigation.Screens
import com.gkgio.borsch_cooker.utils.events.UserProfileChanged
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.domain.auth.AuthUseCase
import com.gkgio.domain.auth.Cooker
import com.gkgio.domain.profile.LoadProfileUseCase
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    private val authUseCase: AuthUseCase,
    private val userProfileChanged: UserProfileChanged,
    private val loadProfileUseCase: LoadProfileUseCase,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()

    init {
        state.value = State()

        loadProfile()

        userProfileChanged
            .getEventResult()
            .applySchedulers()
            .subscribe({
                loadProfile()
            }, {
                Timber.e(it)
            }).addDisposable()
    }

    fun loadProfile() {
        loadProfileUseCase
            .loadProfile()
            .applySchedulers()
            .doOnSubscribe { state.value = state.nonNullValue.copy(isLoading = true) }
            .subscribe({
                state.value = state.nonNullValue.copy(
                    isLoading = false,
                    isInitialError = false,
                    cooker = it
                )
            }, { throwable ->
                state.value = state.nonNullValue.copy(
                    isLoading = false,
                    isInitialError = true
                )
                processThrowable(throwable)
            }).addDisposable()
    }


    fun onChangeAvatarClicked() {
        router.navigateTo(Screens.InputAvatarFragmentScreen)
    }

    fun onChangePhoneClicked() {
        router.navigateTo(Screens.InputPhoneFragmentScreen(false))
    }


    fun onChangeAddressClicked() {
        router.navigateTo(Screens.FindAddressFragmentScreen)
    }

    fun onChangeNameClicked() {
        router.navigateTo(Screens.InputNameFragmentScreen)
    }

    data class State(
        val isLoading: Boolean = false,
        val isInitialError: Boolean = false,
        val cooker: Cooker? = null
    )
}