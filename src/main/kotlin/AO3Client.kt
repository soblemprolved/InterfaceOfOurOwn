import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import service.models.AO3Error
import service.models.AO3Response
import service.requests.AO3Request
import service.requests.GetRequest

/**
 * The main access point to AO3. Try to initialise only one (1) instance of this class.
 * Pass in your existing [OkHttpClient] instance to [AO3Client], and [AO3Client] will create a new one from it
 * with the specifications it needs.
 */
class AO3Client(
    val okHttpClient: OkHttpClient
) {
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
            val (value, csrfToken) = request.converter.convert(response)
            return AO3Response.Success(value, csrfToken)
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
            val (value, csrfToken) = request.converter.convert(response)
            return AO3Response.Success(value, csrfToken)
        } catch (e: IOException) {
            return AO3Response.NetworkError
        } catch (e: AO3Error) {
            return AO3Response.ServerError(e)
        }
    }

    fun <T> enqueue(): T {  // make this cancellable?
        TODO("Not implemented yet")
    }
}
