package com.zeffo.test.network

import com.google.gson.annotations.SerializedName

data class ZeffoResponse<T> (
    @SerializedName("error") val error: String?,
    @SerializedName("data") val data: T
)