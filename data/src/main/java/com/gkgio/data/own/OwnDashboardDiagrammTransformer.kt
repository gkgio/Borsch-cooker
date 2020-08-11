package com.gkgio.data.own

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.own.OwnDashboardDiagrammData
import javax.inject.Inject

class OwnDashboardDiagrammTransformer @Inject constructor() :
    BaseTransformer<OwnDashboardDiagrammResponse, OwnDashboardDiagrammData> {
    override fun transform(data: OwnDashboardDiagrammResponse) = with(data) {
        OwnDashboardDiagrammData(
            date,
            count
        )
    }
}