package service.models

sealed class AO3Response<out T> {
    data class Success<out T>(val value: T): AO3Response<T>()
    data class ServerError(val error: AO3Error): AO3Response<Nothing>()
    object NetworkError: AO3Response<Nothing>()
}
