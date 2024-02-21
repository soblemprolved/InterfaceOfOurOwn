package com.soblemprolved.interfaceofourown.service.response

import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Call
import retrofit2.Response

/**
 * Represents a response from the service. An [AO3Response] can either be a [Success] or a [Failure].
 *
 * A [Success] response encapsulates the deserialised response obtained from a successful request
 * in its [Success.value].
 *
 * A [Failure] response encapsulates the specific error thrown when the request fails for any reason
 * in its [Failure.error].
 */
sealed class AO3Response<out T> {
    /**
     * Represents a success scenario. The expected result of the call is represented by [value].
     */
    data class Success<out T>(val value: T): AO3Response<T>()

    /**
     * Represents a scenario where AO3 returns a response that does not indicate success (i.e. not a 2XX response).
     * The meaning of the [error] is context-specific; further type-checking should be done in order to
     * discern its meaning.
     */
    data class Failure<T>(val error: AO3Error): AO3Response<T>() {
        constructor(call: Call<T>, response: Response<T>) : this(retrieveError(call, response))

        companion object {
            private fun <T> retrieveError(call: Call<T>, response: Response<T>): AO3Error {
                // TODO: handle all the errors in here
                // technically we can hardcode the response to look for certain headers
                // then we can return the subtypes of, say, redirecterror
                return when (response.code()) {
                    302, 304    -> with (response.headers()["location"] ?: return AO3Error.NotFoundError) {
                                       val redirectUrlSegments = this.toHttpUrl().pathSegments
                                       val requestUrlPathSegments = call.request().url.pathSegments

                                       if (requestUrlPathSegments.first() == "tags") {
                                           if (redirectUrlSegments.last() == "works") {
                                               val mainTag = redirectUrlSegments[redirectUrlSegments.lastIndex - 1]
                                               AO3Error.TagSynonymError(mainTag, this)
                                           } else {
                                               AO3Error.TagNotFilterableError(this)
                                           }
                                       } else if (requestUrlPathSegments.first() == "works") {
                                           if (this == "https://archiveofourown.org/users/login?restricted=true") {
                                               // TODO: honestly this seems like it will appear again
                                               // TODO: we might have to move this out
                                               AO3Error.NotLoggedInError
                                           } else {
                                               AO3Error.GenericRedirectError(this)
                                           }
                                       } else {
                                           AO3Error.GenericRedirectError(this)
                                       }
                                   }
                    404         -> AO3Error.NotFoundError
                    429         -> AO3Error.TooManyRequestsError
                    503         -> AO3Error.ServiceUnavailableError
                    in 500..599 ->  TODO("Not implemented yet")
                    else ->         TODO("Not implemented yet")
                }
            }
        }
    }
}
