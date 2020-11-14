package com.gkgio.borsch_cooker.profile.address

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.ext.applySchedulers
import com.gkgio.borsch_cooker.ext.nonNullValue
import com.gkgio.borsch_cooker.utils.SingleLiveEvent
import com.gkgio.borsch_cooker.utils.events.UserProfileChanged
import com.gkgio.domain.address.*
import com.gkgio.domain.analytics.AnalyticsRepository
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class FindAddressViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    private val loadAddressesUseCase: LoadAddressesUseCase,
    private val userProfileChanged: UserProfileChanged,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()
    val closeKeyBoard = SingleLiveEvent<Unit>()

    init {
        state.value = State(false)
    }

    fun onSearchTextChanged(searchText: String) {
        loadAddressesUseCase
            .loadGeoSuggestions(
                GeoSuggestionsRequest(
                    query = searchText,
                    count = 8
                )
            )
            .applySchedulers()
            .doOnSubscribe { state.value = state.nonNullValue.copy(isProgress = true) }
            .subscribe({
                state.value =
                    state.nonNullValue.copy(isProgress = false, geoSuggestionList = it.suggestions)
            }, {
                state.value = state.nonNullValue.copy(isProgress = false)
                closeKeyBoard.call()
                processThrowable(it)
            }).addDisposable()
    }

    fun onAddressSelectClick(geoSuggestion: GeoSuggestion) {
        loadAddressesUseCase
            .loadGeoSuggestions(
                GeoSuggestionsRequest(
                    query = geoSuggestion.value
                )
            ).flatMapCompletable { geoSuggestionList ->
                loadAddressesUseCase
                    .addNewClientAddress(
                        with(geoSuggestionList.suggestions[0].data) {
                            AddressAddingRequest(
                                city = city,
                                country = country,
                                flat = null,
                                house = house,
                                location = Coordinates(
                                    geo_lat!!.toDouble(),
                                    geo_lon!!.toDouble()
                                ),
                                street = streetWithType,
                                block = block,
                                region = region,
                                cityArea = city_area,
                                cityDistrict = city_district
                            )
                        }
                    )
            }
            .applySchedulers()
            .doOnSubscribe { state.value = state.nonNullValue.copy(isProgress = true) }
            .subscribe({
                state.value = state.nonNullValue.copy(isProgress = false)
                userProfileChanged.onComplete("")
                onBackClick()
            }, { throwable ->
                state.value = state.nonNullValue.copy(isProgress = false)
                processThrowable(throwable)
            }).addDisposable()

    }

    data class State(
        val isProgress: Boolean,
        val geoSuggestionList: List<GeoSuggestion>? = null
    )
}