package com.gkgio.borsch_cooker.profile.avatar

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.ext.applySchedulers
import com.gkgio.borsch_cooker.utils.SingleLiveEvent
import com.gkgio.borsch_cooker.utils.events.UserProfileChanged
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.domain.auth.AuthUseCase
import ru.terrakok.cicerone.Router
import java.io.File
import javax.inject.Inject


class InputAvatarViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    private val authUseCase: AuthUseCase,
    private val userProfileChanged: UserProfileChanged,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val photoUri = MutableLiveData<File?>()
    val progressState = SingleLiveEvent<Boolean>()

    fun onPhotoLoaded(file: File?) {
        file?.let {
            photoUri.value = it
        }
    }

    fun onSaveBtnClicked() {
        photoUri.value?.let {
            photoUri.value = it

            authUseCase
                .saveAvatar(it)
                .applySchedulers()
                .doOnSubscribe { progressState.value = true }
                .subscribe({
                    progressState.value = false
                    userProfileChanged.onComplete("")
                    router.exit()
                }, { throwable ->
                    progressState.value = false
                    processThrowable(throwable)
                }).addDisposable()

        }
    }
}