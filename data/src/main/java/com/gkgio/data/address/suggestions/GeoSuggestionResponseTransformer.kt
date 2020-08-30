package com.gkgio.data.address.suggestions

import com.gkgio.data.BaseTransformer
import com.gkgio.data.address.suggestions.GeoSuggestionDataResponseTransformer
import com.gkgio.data.address.suggestions.GeoSuggestionResponse
import com.gkgio.domain.address.GeoSuggestion
import javax.inject.Inject

class GeoSuggestionResponseTransformer @Inject constructor(
    private val geoSuggestionDataResponseTransformer: GeoSuggestionDataResponseTransformer
) : BaseTransformer<GeoSuggestionResponse, GeoSuggestion> {

    override fun transform(data: GeoSuggestionResponse) = with(data) {
        GeoSuggestion(
            geoSuggestionDataResponseTransformer.transform(this.data),
            value
        )
    }
}