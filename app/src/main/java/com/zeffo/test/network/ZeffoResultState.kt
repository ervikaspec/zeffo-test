package com.zeffo.test.network

sealed class ZeffoResultState<T> {

    data class Loading<T>(val showLoader: Boolean) : ZeffoResultState<T>()

    data class Success<T>(val data: T) : ZeffoResultState<T>()

    data class Error<T>(val error: ZeffoError) : ZeffoResultState<T>()

}

data class ZeffoError(val errorCode: String?, val errorMessage: String?)
