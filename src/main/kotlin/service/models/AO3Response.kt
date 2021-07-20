package service.models

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
    data class ServerError(val error: AO3Error): AO3Response<Nothing>()

    /**
     * Represents a scenario where the network is encountering connectivity issues.
     */
    object NetworkError: AO3Response<Nothing>()
}
