package com.soblemprolved.orpheus.service.models

sealed class AO3Error: Throwable() {

    /**
     * Represents a scenario where AO3 returns a response prompting for a redirect (e.g. 302, 304).
     */
    sealed class RedirectError(open val redirectUrl: String): AO3Error()
        /**
         * Represents a scenario where AO3 is redirecting the client to the works of another tag, in response to
         * a request for the works of a particular tag. This indicates that these two tags are synonyms, and the
         * destination tag is the canonical name for the original requested tag.
         */
        data class TagSynonymError(val synonymTag: String, override val redirectUrl: String) : RedirectError(redirectUrl)

        /**
         * Represents a scenario where AO3 does not allow the client to filter on the tag. This is usually because
         * the tag has not been marked common, and therefore it cannot be filtered on.
         */
        data class TagNotFilterableError(override val redirectUrl: String): RedirectError(redirectUrl)

    /**
     * Represents a 404 error.
     */
    object NotFoundError: AO3Error()

    /**
     * Represents a 429 error.
     */
    object TooManyRequestsError: AO3Error()

    object ServerSideError: AO3Error()

    /**
     * Represents a situation where the content can only be accessed if the user is logged in.
     */
    object NotLoggedInError: AO3Error()
}
