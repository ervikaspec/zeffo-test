package com.zeffo.test.features.smsreader.repository

import com.zeffo.test.features.smsreader.api.TxnService
import com.zeffo.test.features.smsreader.dto.SaveTxnRequest
import com.zeffo.test.features.smsreader.dto.TxnResponseDto
import com.zeffo.test.network.ResponseCallback
import com.zeffo.test.network.ResponseParser
import com.zeffo.test.network.RetrofitClient
import com.zeffo.test.network.ZeffoResultState

object TxnRepository {

    private val api: TxnService by lazy {
        RetrofitClient.retrofitClient
            .build()
            .create(TxnService::class.java)
    }

    fun callTxnAPI(dao: SaveTxnRequest, callback: ResponseCallback<ZeffoResultState<TxnResponseDto>>) {
        val call = api.saveTxn(dao)
        call.enqueue(ResponseParser.parse(callback))
    }
}