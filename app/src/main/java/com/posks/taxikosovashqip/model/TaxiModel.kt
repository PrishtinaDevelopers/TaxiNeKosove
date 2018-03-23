package com.posks.taxikosovashqip.model

data class TaxiModel(
        var taxiName: String? = null,
        var cityName: String? = null,
        var numberVala: String? = null,
        var numberIpko: String? = null,
        var viberOption: Int = 0
)