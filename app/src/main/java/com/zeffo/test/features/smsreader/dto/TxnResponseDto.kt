package com.zeffo.test.features.smsreader.dto

import com.google.gson.annotations.SerializedName

data class TxnResponseDto(
    @SerializedName("txn_id") val txn_id: Int
)