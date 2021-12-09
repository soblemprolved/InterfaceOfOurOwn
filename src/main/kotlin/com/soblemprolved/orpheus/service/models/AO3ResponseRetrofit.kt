package com.soblemprolved.orpheus.service.models

import retrofit2.Response

sealed class AO3ResponseRetrofit<out T> {
    /**
     * Represents a success scenario. The expected result of the call is represented by [value].
     */
    data class Success<out T>(val value: T): AO3ResponseRetrofit<T>()

    /**
     * Represents a scenario where AO3 returns a response that does not indicate success (i.e. not a 2XX response).
     * The meaning of the [error] is context-specific; further type-checking should be done in order to
     * discern its meaning.
     */
    data class Failure<T>(val errorCode: Int, val response: Response<T>): AO3ResponseRetrofit<T>()

    /**
     * Represents a scenario where the network is encountering connectivity issues.
     */
    object NetworkError: AO3ResponseRetrofit<Nothing>()

    /**
     * Represents a scenario where the call fails, but it is not due to network - i.e. a truly unexpected failure.
     */
    object UnknownError: AO3ResponseRetrofit<Nothing>()
}
