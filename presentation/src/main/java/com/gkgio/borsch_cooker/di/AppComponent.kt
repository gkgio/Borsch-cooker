package com.gkgio.borsch_cooker.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.gkgio.borsch_cooker.auth.InputPhoneViewModel
import com.gkgio.borsch_cooker.auth.ValidatePhoneViewModel
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.domain.auth.AuthRepository
import com.gkgio.domain.theme.ThemeRepository
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.base.bottomsheet.BaseBottomSheetDialog
import com.gkgio.borsch_cooker.meals.MealsViewModel
import com.gkgio.borsch_cooker.own.OwnViewModel
import com.gkgio.borsch_cooker.main.LaunchActivity
import com.gkgio.borsch_cooker.main.LaunchViewModel
import com.gkgio.borsch_cooker.main.MainViewModel
import com.gkgio.borsch_cooker.onboarding.OnboardingViewModel
import com.gkgio.borsch_cooker.orders.OrdersListViewModel
import com.gkgio.borsch_cooker.orders.OrdersViewModel
import com.gkgio.borsch_cooker.support.SupportViewModel
import com.gkgio.borsch_cooker.support.chat.SupportChatViewModel
import com.gkgio.domain.errorreporter.ErrorReporter
import com.squareup.moshi.Moshi
import dagger.Component
import retrofit2.Retrofit
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NavigationModule::class,
        AppModule::class,
        AppModule::class,
        AuthModule::class,
        AnalyticsModule::class,
        ThemeModule::class,
        SupportModule::class,
        OrdersModule::class
    ]
)
interface AppComponent {
    fun inject(app: Application)

    fun inject(launchActivity: LaunchActivity)
    fun inject(baseFragment: BaseFragment<BaseViewModel>)
    fun inject(baseBottomSheetDialog: BaseBottomSheetDialog)

    val launchViewModel: LaunchViewModel
    val mainViewModel: MainViewModel
    val mealsViewModel: MealsViewModel
    val ownViewModel: OwnViewModel
    val supportViewModel: SupportViewModel
    val onboardingViewModel: OnboardingViewModel
    val supportChatViewModel: SupportChatViewModel
    val ordersViewModel: OrdersViewModel
    val ordersListViewModel: OrdersListViewModel
    val inputPhoneViewModel: InputPhoneViewModel
    val validatePhoneViewModel: ValidatePhoneViewModel

    val context: Context
    val moshi: Moshi
    val retrofit: Retrofit
    val prefs: SharedPreferences

    // Repositories
    val analyticsRepository: AnalyticsRepository
    val authRepository: AuthRepository
    val themeRepository: ThemeRepository


    val errorReporter: ErrorReporter

    //event

    //Cicerone
    val router: Router
    val navigatorHolder: NavigatorHolder
}