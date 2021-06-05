package com.zeffo.test.features.smsreader.dto

import com.google.gson.annotations.SerializedName

data class SaveTxnRequest(
    @SerializedName("device_id") val device_id: Long,
    @SerializedName("user_id") val user_id: String,
    @SerializedName("transaction") val transaction: TxnDetailsDto
    )