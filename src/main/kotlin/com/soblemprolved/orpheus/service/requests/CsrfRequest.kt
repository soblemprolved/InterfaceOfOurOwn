package com.soblemprolved.orpheus.service.requests

import com.soblemprolved.orpheus.service.old.converters.CsrfConverter
import com.soblemprolved.orpheus.service.requests.AO3Request.Companion.HTML_HEADERS
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

/**
 * A request to extract the CSRF token from the given url.
 */
class CsrfRequest(
    override val url: HttpUrl
) : GetRequest<String> {
    constructor(url: String) : this(url.toHttpUrl())

    override val headers = HTML_HEADERS
    override val converter = CsrfConverter
}
