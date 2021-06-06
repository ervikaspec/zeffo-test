package com.zeffo.test.features.smsreader.dto

import com.google.gson.annotations.SerializedName

data class TxnDetailsDto(
    @SerializedName("amount") val amount: Double,
    @SerializedName("merchant") val merchant: String?,
    @SerializedName("card") val card: String?
)