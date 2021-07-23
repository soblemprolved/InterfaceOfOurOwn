package com.soblemprolved.orpheus

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import com.soblemprolved.orpheus.service.models.AO3Error
import com.soblemprolved.orpheus.service.models.AO3Response
import com.soblemprolved.orpheus.service.requests.AO3Request
import com.soblemprolved.orpheus.service.requests.GetRequest

/**
 * The main access point to AO3. Try to initialise only one (1) instance of this class.
 * Pass in your existing [OkHttpClient] instance to [AO3Client], and [AO3Client] will create a new one from it
 * with the specifications it needs.
 */
class AO3Client(
    userOkHttpClient: OkHttpClient
) {
    // TODO: the AO3 client should know about the converters, not the request. Requests can be made independent of converters.
    // either way, converters have to be specified by the user in the same line or next line.

    private val okHttpClient = userOkHttpClient.newBuilder()
        .followRedirects(false)
        .build()

    /**
     * Suspends the current thread and executes the network request in [Dispatchers.IO].
     */
    suspend fun <T> execute(request: AO3Request<T>): AO3Response<T> {
        val okHttpRequest = Request.Builder()
            .url(request.url)
            .headers(request.headers)
            .let {
                when (request) {
                    is GetRequest -> it
//                    is PostRequest -> it.post()
                }
            }
            .build()

        val call = okHttpClient.newCall(okHttpRequest)

        try {
            val response = withContext(Dispatchers.IO) { call.execute() }
            val value = request.converter.convert(response)
            return AO3Response.Success(value)
        } catch (e: IOException) {
            return AO3Response.NetworkError
        } catch (e: AO3Error) {
            return AO3Response.ServerError(e)
        }
    }

    /**
     * Blocks the current thread and executes the network request in [Dispatchers.IO].
     *
     * Do not call this method from within a coroutine; use the suspending [execute] method instead.
     */
    fun <T> executeBlocking(request: AO3Request<T>): AO3Response<T> {
        val okHttpRequest = Request.Builder()
            .url(request.url)
            .headers(request.headers)
            .let {
                when (request) {
                    is GetRequest -> it
//                    is PostRequest -> it.post()
                }
            }
            .build()

        val call = okHttpClient.newCall(okHttpRequest)

        try {
            val response = runBlocking(Dispatchers.IO) { call.execute() }
            val value = request.converter.convert(response)
            return AO3Response.Success(value)
        } catch (e: IOException) {
            return AO3Response.NetworkError
        } catch (e: AO3Error) {
            return AO3Response.ServerError(e)
        }
    }

    fun <T> enqueue(): T {  // make this cancellable? remember to add the callback parameter
        TODO("Not implemented yet")
        // enqueue requires me to return a cancellable call/promise from the client.
        // it would be better UX to copy retrofit's call architecture, but I dont need that complexity.
        // so take in a callback, create a network request and pass it to the call
        // the call will immediately execute?
        // honestly this is not a priority at all
    }
}
