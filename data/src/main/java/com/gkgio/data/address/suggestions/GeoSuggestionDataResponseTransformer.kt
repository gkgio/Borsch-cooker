package com.gkgio.data.address.suggestions

import com.gkgio.data.BaseTransformer
import com.gkgio.data.address.suggestions.GeoSuggestionDataResponse
import com.gkgio.domain.address.GeoSuggestionData
import javax.inject.Inject

class GeoSuggestionDataResponseTransformer @Inject constructor() :
    BaseTransformer<GeoSuggestionDataResponse, GeoSuggestionData> {

    override fun transform(data: GeoSuggestionDataResponse) = with(data) {
        GeoSuggestionData(
            city,
            cityArea,
            cityDistrict,
            cityTypeFull,
            country,
            flat,
            geoLat,
            geoLon,
            house,
            region,
            streetWithType,
            block
        )
    }
}