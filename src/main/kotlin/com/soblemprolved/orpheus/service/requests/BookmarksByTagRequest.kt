package com.soblemprolved.orpheus.service.requests

import com.soblemprolved.orpheus.model.BookmarkFilterParameters
import com.soblemprolved.orpheus.service.converters.Converter
import com.soblemprolved.orpheus.service.requests.AO3Request.Companion.BASE_HTTP_URL_BUILDER_CONFIGURATION
import com.soblemprolved.orpheus.service.requests.AO3Request.Companion.HTML_HEADERS

class BookmarksByTagRequest<T>(
    val tag: String,
    val filterParameters: BookmarkFilterParameters,
    val page: Int,
    override val converter: Converter<T>
) : GetRequest<T> {
    override val url = BASE_HTTP_URL_BUILDER_CONFIGURATION
        .addPathSegment("tags")
        .addPathSegment(encodeTag(tag))
        .addPathSegment("bookmarks")
        // TODO: add filter parameters
        .addQueryParameter("page", page.toString())
        .build()

    override val headers = HTML_HEADERS

    companion object {
        private fun encodeTag(tag: String): String {
            return tag.replace("/", "*s*")
                .replace("&", "*a*")
                .replace(".", "*d*")
                .replace("?", "*q*")
                .replace("#", "*h*")
        }
    }
}