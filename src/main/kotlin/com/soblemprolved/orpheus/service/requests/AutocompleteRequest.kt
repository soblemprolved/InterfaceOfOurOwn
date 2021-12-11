package com.soblemprolved.orpheus.service.requests

import okhttp3.HttpUrl
import com.soblemprolved.orpheus.service.old.converters.AutocompleteConverter
import com.soblemprolved.orpheus.service.old.converters.Converter
import com.soblemprolved.orpheus.service.models.AutocompleteType
import com.soblemprolved.orpheus.service.requests.AO3Request.Companion.BASE_HTTP_URL_BUILDER_CONFIGURATION
import com.soblemprolved.orpheus.service.requests.AO3Request.Companion.JSON_HEADERS

class AutocompleteRequest(
    val type: AutocompleteType,
    val query: String,
) : GetRequest<List<String>> {
    override val url: HttpUrl = BASE_HTTP_URL_BUILDER_CONFIGURATION
        .addPathSegment("autocomplete")
        .addPathSegment(type.pathSegment)
        .addQueryParameter("term", query)
        .build()

    override val headers = JSON_HEADERS

    override val converter = AutocompleteConverter
}
