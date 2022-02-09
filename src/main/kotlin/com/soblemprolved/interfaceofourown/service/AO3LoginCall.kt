package com.soblemprolved.interfaceofourown.service

import okio.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * This call is adapted from the solution here: https://stackoverflow.com/a/57816819/16271427
 * This will help us in wrapping the return values of suspend functions in [AO3Response]s.
 */
class AO3LoginCall(proxy: Call<Login>) : CallDelegate<Login, AO3Response<Login>>(proxy) {
    override fun enqueueImpl(callback: Callback<AO3Response<Login>>) = proxy.enqueue(
        object: Callback<Login> {
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                // dynamically dispatch to the correct handler based on the url
                val result = when (response.code()) {
                    302 -> {
                        if (response.headers()["location"] == "https://archiveofourown.org/auth_error") {
                            // TODO: change the error thrown to specify that it is a cookie/session error?
                            // e.g. SessionError
                            AO3Response.Failure(AO3Error.AuthenticationError)
                        } else {
                            AO3Response.Success(Login)
                        }
                    }
                    200 -> AO3Response.Failure(AO3Error.LoginError)
                    else -> AO3Response.Failure(call, response)
                }

                callback.onResponse(this@AO3LoginCall, Response.success(result))
            }

            override fun onFailure(call: Call<Login>, t: Throwable) {
                val result = if (t is IOException) {
                    AO3Response.Failure<Login>(AO3Error.NetworkError)
                } else {
                    AO3Response.Failure<Login>(AO3Error.UnknownClientError)
                }

                callback.onResponse(this@AO3LoginCall, Response.success(result))
            }
        }
    )

    override fun cloneImpl() = AO3LoginCall(proxy.clone())
}
