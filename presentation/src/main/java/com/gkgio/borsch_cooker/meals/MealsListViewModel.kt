package com.gkgio.borsch_cooker.meals

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.ext.isNonInitialized
import com.gkgio.domain.analytics.AnalyticsRepository
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MealsListViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()

    private val testList = mutableListOf<MealsItemUi>()

    fun init(mealsType: String) {
        if (mealsType == "sing5les") {
            testList.add(
                MealsItemUi(
                    1,
                    "Шаурма на углях",
                    "https://img.povar.ru/main/94/1c/00/85/shaurma_na_mangale-577820.JPG",
                    340
                )
            )
            testList.add(
                MealsItemUi(
                    2,
                    "Шаурма на шаурме",
                    "https://i2.wp.com/crispy.news/wp-content/uploads/2019/11/shaurma-svininoj.jpg",
                    440
                )
            )
            testList.add(
                MealsItemUi(
                    3,
                    "Шаурма сырная",
                    "https://eda.yandex/images/1380157/5813ba8cdb0c2a7fec400f8f455d6c63-400x400.jpeg",
                    430
                )
            )
        } else if(mealsType == "lunches") {
            testList.add(
                MealsItemUi(
                    1,
                    "Комбо шаурмы",
                    "https://img.povar.ru/main/94/1c/00/85/shaurma_na_mangale-577820.JPG",
                    740
                )
            )
            testList.add(
                MealsItemUi(
                    2,
                    "Шавернутая шаверма",
                    "https://i2.wp.com/crispy.news/wp-content/uploads/2019/11/shaurma-svininoj.jpg",
                    940
                )
            )
            testList.add(
                MealsItemUi(
                    3,
                    "Семья в комбо",
                    "https://eda.yandex/images/1380157/5813ba8cdb0c2a7fec400f8f455d6c63-400x400.jpeg",
                    230
                )
            )
        }
        if (state.isNonInitialized()) {
            state.value = State(testList, false, false)
        }
    }

    data class State(
        val mealsList: List<MealsItemUi>,
        val isLoading: Boolean,
        val isInitialError: Boolean
    )
}