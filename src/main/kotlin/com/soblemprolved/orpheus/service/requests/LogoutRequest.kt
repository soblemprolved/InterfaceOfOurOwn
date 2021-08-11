package com.soblemprolved.orpheus.service.requests

import com.soblemprolved.orpheus.service.converters.LogoutConverter
import okhttp3.FormBody

internal class LogoutRequest(
    val csrfToken: String
) : PostRequest<Boolean> {
    override val url = AO3Request.BASE_HTTP_URL_BUILDER_CONFIGURATION
        .addPathSegment("users")
        .addPathSegment("logout")
        .build()

    override val headers = AO3Request.HTML_HEADERS

    override val converter = LogoutConverter

    override val requestBody = FormBody.Builder()
        .add("_method", "delete")
        .add("authenticity_token", csrfToken)
        .build()
}
