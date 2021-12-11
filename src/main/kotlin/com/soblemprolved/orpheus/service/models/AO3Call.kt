package com.soblemprolved.orpheus.service.models

import okio.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * This call is adapted from the solution here: https://stackoverflow.com/a/57816819/16271427
 * This will help us in adapting suspend functions.
 */
class AO3Call<T>(proxy: Call<T>) : CallDelegate<T, RetrofitResponse<T>>(proxy) {
    override fun enqueueImpl(callback: Callback<RetrofitResponse<T>>) = proxy.enqueue(object: Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            val code = response.code()
            val result = if (code in 200 until 300) {
                val body = response.body()!!
                val successResult: RetrofitResponse<T> = RetrofitResponse.Success(body) // unnecessary repeating code?
                successResult
            } else {
                RetrofitResponse.Failure(code, response)
            }

            callback.onResponse(this@AO3Call, Response.success(result))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            val result = if (t is IOException) {
                RetrofitResponse.NetworkError
            } else {
                RetrofitResponse.UnknownError
            }

            callback.onResponse(this@AO3Call, Response.success(result))
        }
    })

    override fun cloneImpl() = AO3Call(proxy.clone())
}
