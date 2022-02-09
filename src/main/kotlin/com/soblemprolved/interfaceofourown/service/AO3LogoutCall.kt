package com.soblemprolved.interfaceofourown.service

import okio.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * This call is adapted from the solution here: https://stackoverflow.com/a/57816819/16271427
 * This will help us in wrapping the return values of suspend functions in [AO3Response]s.
 */
class AO3LogoutCall(proxy: Call<Logout>) : CallDelegate<Logout, AO3Response<Logout>>(proxy) {
    override fun enqueueImpl(callback: Callback<AO3Response<Logout>>) = proxy.enqueue(
        object: Callback<Logout> {
            override fun onResponse(call: Call<Logout>, response: Response<Logout>) {
                // dynamically dispatch to the correct handler based on the url
                val result = when (response.code()) {
                    302 -> {
                        if (response.headers()["location"] == "https://archiveofourown.org/auth_error") {
                            AO3Response.Failure(AO3Error.AuthenticationError)
                        } else {
                            AO3Response.Success(Logout)
                        }
                    }
                    else -> AO3Response.Failure(call, response)
                }

                callback.onResponse(this@AO3LogoutCall, Response.success(result))
            }

            override fun onFailure(call: Call<Logout>, t: Throwable) {
                val result = if (t is IOException) {
                    AO3Response.Failure<Logout>(AO3Error.NetworkError)
                } else {
                    AO3Response.Failure<Logout>(AO3Error.UnknownClientError)
                }

                callback.onResponse(this@AO3LogoutCall, Response.success(result))
            }
        }
    )

    override fun cloneImpl() = AO3LogoutCall(proxy.clone())
}
