package com.soblemprolved.orpheus.service.requests

import com.soblemprolved.orpheus.service.old.converters.Converter
import com.soblemprolved.orpheus.service.old.converters.LoginConverter
import com.soblemprolved.orpheus.service.requests.AO3Request.Companion.BASE_HTTP_URL_BUILDER_CONFIGURATION
import com.soblemprolved.orpheus.service.requests.AO3Request.Companion.HTML_HEADERS
import okhttp3.FormBody
import okhttp3.Headers
import okhttp3.RequestBody

/**
 * Logs the user into AO3.
 *
 * Warning: executing this will change the state of the client to a "logged in" state.
 */
internal class LoginRequest(
    val username: String,
    val password: String,
    val csrfToken: String
) : PostRequest<Boolean> {
    override val url = BASE_HTTP_URL_BUILDER_CONFIGURATION
        .addPathSegment("users")
        .addPathSegment("login")
        .build()

    override val headers = HTML_HEADERS

    override val converter = LoginConverter

    override val requestBody = FormBody.Builder()
        .add("utf8", "✓")
        .add("authenticity_token", csrfToken)
        .add("user[login]", username)
        .add("user[password]", password)
        .add("user[remember_me]", "1")  // for easier persistence
        .add("commit", "Log In")
        .build()
}
