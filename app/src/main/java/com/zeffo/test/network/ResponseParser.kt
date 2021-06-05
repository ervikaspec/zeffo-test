package com.zeffo.test.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ResponseParser {

    fun <T> parse(callback: ResponseCallback<ZeffoResultState<T>>): Callback<ZeffoResponse<T>> {
        return object: Callback<ZeffoResponse<T>> {
            override fun onFailure(call: Call<ZeffoResponse<T>>, t: Throwable) {
                Log.v("SMSReader : ", t.message.toString())
                callback.onResponse(ZeffoResultState.Error(ZeffoError(t.cause.toString(), t.message)))
            }

            override fun onResponse(
                call: Call<ZeffoResponse<T>>,
                response: Response<ZeffoResponse<T>>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.v("SMSReader : ", response.body().toString())
                    callback.onResponse(ZeffoResultState.Success(body.data))
                } else if (!response.isSuccessful){
                    callback.onResponse(parseErrorResponse(response))
                }
            }
        }
    }

    private fun <T> parseErrorResponse(resp: Response<ZeffoResponse<T>>): ZeffoResultState<T> {
        if (resp.code() != 404) {
            return ZeffoResultState.Error(ZeffoError(resp.code().toString(), "API not found"))
        }
        return ZeffoResultState.Error(ZeffoError(resp.code().toString(), "Something went wrong"))
    }
}

interface ResponseCallback <T> {
    fun onResponse(resp: T)
}