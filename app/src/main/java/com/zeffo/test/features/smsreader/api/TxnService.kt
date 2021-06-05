package com.zeffo.test.features.smsreader.api

import com.zeffo.test.features.smsreader.dto.SaveTxnRequest
import com.zeffo.test.features.smsreader.dto.TxnResponseDto
import com.zeffo.test.network.ApiConstants
import com.zeffo.test.network.ZeffoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface TxnService {
    @POST(ApiConstants.SAVE_NEW_TXN)
    fun saveTxn(@Body req: SaveTxnRequest): Call<ZeffoResponse<TxnResponseDto>>
}