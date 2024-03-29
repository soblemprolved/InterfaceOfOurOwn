package com.soblemprolved.interfaceofourown.service.response

/**
 * Represents all the possible errors that may crop up in the process of interacting with AO3.
 */
sealed class AO3Error: Throwable() {
    sealed class ConnectionError: AO3Error()
        /**
         * Represents a scenario where the network is encountering connectivity issues.
         */
        object NetworkError: ConnectionError()
        object UnknownClientError: ConnectionError()

    sealed class HttpError: AO3Error()
        /**
         * Represents a scenario where AO3 returns a response prompting for a redirect (e.g. 302, 304).
         */
        sealed class RedirectError(open val redirectUrl: String): HttpError()
            /**
             * Represents a scenario where AO3 is redirecting the client to the works of another tag, in response to
             * a request for the works of a particular tag. This indicates that these two tags are synonyms, and the
             * destination tag is the canonical name for the original requested tag.
             */
            data class TagSynonymError(
                val synonymTag: String,
                override val redirectUrl: String
            ) : RedirectError(redirectUrl)

            /**
             * Represents a scenario where AO3 does not allow the client to filter on the tag. This is usually because
             * the tag has not been marked common, and therefore it cannot be filtered on.
             */
            data class TagNotFilterableError(override val redirectUrl: String): RedirectError(redirectUrl)

            data class GenericRedirectError(override val redirectUrl: String): RedirectError(redirectUrl)

        /**
         * Represents a 404 error.
         */
        object NotFoundError: HttpError()

        /**
         * Represents a 429 error.
         */
        object TooManyRequestsError: HttpError()

        /**
         * Represents a 503 error.
         */
        object ServiceUnavailableError: HttpError()

        /**
         * Represents a situation where the content can only be accessed if the user is logged in.
         */
        object NotLoggedInError: HttpError()

        /**
         * Represents a http error that is not handled
         */
        object UnknownHttpError: HttpError()

        /**
         * Represents a failure to log the user in due to a mismatch in credentials.
         */
        object LoginError: HttpError()

        /**
         * Represents an error that arises from having mismatched CSRF tokens and session cookies.
         *
         * This can be resolved by retrieving a new CSRF token and session cookie using getCsrfToken().
         */
        object AuthenticationError: HttpError()
}
