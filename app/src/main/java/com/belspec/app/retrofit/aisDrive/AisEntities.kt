package com.belspec.app.retrofit.aisDrive

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class AisGps(
        @Expose val latitude: String,
        @Expose val longitude: String)

data class Description(
        @Expose val gps: AisGps,
        @Expose val carNumber: String,
        @Expose val categoriesGoods: String,
        @Expose val address: String
)

data class AisAdministrator(
        @Expose val  name: String?,
        @Expose val phone: String?,
        @Expose val active: Int
)
data class AisResponse(
        @Expose val createdAt: String,
        @Expose val updatedAt: String,
        @Expose val id: Long,
        @Expose val accountGps: List<String>,
        @Expose val account: Any?,
        @Expose val categoriesGoods: Any?,
        @Expose val objects: List<Any>,
        @Expose val title: String,
        @Expose val description: Description,
        @Expose val address: String,
        @Expose val active: Int,
        @Expose val wasPaidByCash: Int,
        @Expose val calculatePrice: BigDecimal,
        @Expose val discount: BigDecimal,
        @Expose val manualPrice: BigDecimal,
        @Expose val distance: Double,
        @SerializedName("object")
        @Expose val _object: Any?,
        @Expose val administrator: AisAdministrator?,
        @Expose val billing: Any?
)

    const val CANCELED = -1
    const val NOT_IN_PROGRESS = 0
    const val IN_PROGRESS = 1
    const val EVACUATED = 2
    const val PARKED = 3