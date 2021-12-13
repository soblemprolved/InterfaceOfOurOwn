package com.soblemprolved.orpheus.service.models

import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Call
import retrofit2.Response

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
    data class Failure<T>(val call: Call<T>, val response: Response<T>): AO3Response<T>() {
        // TODO: handle all the errors in here
        // technically we can hardcode the response to look for certain headers
        // then we can return the subtypes of, say, redirecterror
        val error: AO3Error = when (response.code()) {
            302, 304 ->     with (response.headers()["location"] ?: throw AO3Error.NotFoundError) {
                                val redirectUrlSegments = this.toHttpUrl().pathSegments
                                val requestUrlPathSegments = call.request().url.pathSegments

                                if (requestUrlPathSegments.first() == "tags") {
                                    if (redirectUrlSegments.last() == "works") {
                                        val mainTag = redirectUrlSegments[redirectUrlSegments.lastIndex - 1]
                                        throw AO3Error.TagSynonymError(mainTag, this)
                                    } else {
                                        throw AO3Error.TagNotFilterableError(this)
                                    }
                                } else if (requestUrlPathSegments.first() == "works") {
                                    if (this == "https://archiveofourown.org/users/login?restricted=true") {
                                        // TODO: honestly this seems like it will appear again
                                        throw AO3Error.NotLoggedInError
                                    } else {
                                        throw AO3Error.GenericRedirectError(this)
                                    }
                                } else {
                                    throw AO3Error.GenericRedirectError(this)
                                }
                            }
            404 ->          throw AO3Error.NotFoundError
            429 ->          throw AO3Error.TooManyRequestsError
            in 500..599 ->  throw AO3Error.ServerSideError
            else ->         TODO("Not implemented yet")
        }
    }

    /**
     * Represents a scenario where the network is encountering connectivity issues.
     */
    object NetworkError: AO3Response<Nothing>()

    /**
     * Represents a scenario where the call fails, but it is not due to network - i.e. a truly unexpected failure.
     */
    object UnknownError: AO3Response<Nothing>()
}
