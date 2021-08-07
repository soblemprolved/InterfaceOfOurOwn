package com.soblemprolved.orpheus.service.requests

import com.soblemprolved.orpheus.model.CollectionFilterParameters
import com.soblemprolved.orpheus.service.converters.CollectionsSearchConverter
import com.soblemprolved.orpheus.service.converters.Converter
import com.soblemprolved.orpheus.service.query.CollectionFilterQueryMap
import com.soblemprolved.orpheus.service.requests.AO3Request.Companion.BASE_HTTP_URL_BUILDER_CONFIGURATION
import com.soblemprolved.orpheus.service.requests.AO3Request.Companion.HTML_HEADERS

class CollectionsSearchRequest(
    val filterParameters: CollectionFilterParameters,
    val page: Int,
) : GetRequest<CollectionsSearchConverter.Result> {
    override val url = BASE_HTTP_URL_BUILDER_CONFIGURATION
        .addPathSegment("collections")
        .let {
            val queryMap = CollectionFilterQueryMap(filterParameters)
            queryMap.entries.fold(it) { acc, entry ->
                acc.addQueryParameter(entry.key, entry.value)
            }
        }
        .addQueryParameter("page", page.toString())
        .build()

    override val headers = HTML_HEADERS

    override val converter = CollectionsSearchConverter
}
