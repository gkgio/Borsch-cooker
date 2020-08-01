package com.gkgio.borsch_cooker.auth

import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.navigation.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class InputPhoneViewModel @Inject constructor(
    private val router: Router,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    fun onSendCodeBtnClick(inputPhone: String?) {
        if (inputPhone != null) {
            router.navigateTo(Screens.ValidatePhoneFragmentScreen(inputPhone))
        }
    }
}