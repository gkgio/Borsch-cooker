package com.gkgio.borsch_cooker.main

import android.content.Intent
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.domain.theme.ThemeRepository
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.navigation.Screens
import com.gkgio.borsch_cooker.utils.SingleLiveEvent
import com.gkgio.domain.auth.AuthUseCase
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class LaunchViewModel @Inject constructor(
    private val router: Router,
    private val themeRepository: ThemeRepository,
    private val authUseCase: AuthUseCase,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val checkDeepLing = SingleLiveEvent<Intent>()

    fun init(intent: Intent?) {
        if (authUseCase.getAuthToken() != null) {
            router.newRootScreen(Screens.MainFragmentScreen)
        } else {
            router.newRootScreen(Screens.InputPhoneFragmentScreen)
        }
    }

    fun onOpenSupportChatDeepLink() {
        router.switchTo(R.id.tab_support)
    }
}