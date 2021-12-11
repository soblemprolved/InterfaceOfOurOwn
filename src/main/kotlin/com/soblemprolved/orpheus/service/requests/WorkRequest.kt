package com.soblemprolved.orpheus.service.requests

import com.soblemprolved.orpheus.service.old.converters.Converter
import com.soblemprolved.orpheus.service.old.converters.WorkConverter
import com.soblemprolved.orpheus.service.requests.AO3Request.Companion.BASE_HTTP_URL_BUILDER_CONFIGURATION
import com.soblemprolved.orpheus.service.requests.AO3Request.Companion.HTML_HEADERS

class WorkRequest(
    val id: Long,
): GetRequest<WorkConverter.Result> {
    init {
        require(id >= 0) { "ID cannot be negative!" }
    }

    override val url = BASE_HTTP_URL_BUILDER_CONFIGURATION
        .addPathSegment("works")
        .addEncodedPathSegment(id.toString())
        .addQueryParameter("view_adult", "true")
        .addQueryParameter("view_full_work", "true")
        .build()

    override val headers = HTML_HEADERS

    override val converter = WorkConverter
}
