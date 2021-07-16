package service.models

sealed class AO3Error: Throwable() {
    data class RedirectError(val redirectUrl: String): AO3Error()
    object NotFoundError: AO3Error()
}
