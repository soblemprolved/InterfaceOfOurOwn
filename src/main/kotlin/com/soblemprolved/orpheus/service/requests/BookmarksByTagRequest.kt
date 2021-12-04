package com.soblemprolved.orpheus.service.requests

import com.soblemprolved.orpheus.model.BookmarkFilterParameters
import com.soblemprolved.orpheus.service.converters.BookmarksByTagConverter
import com.soblemprolved.orpheus.service.converters.Converter
import com.soblemprolved.orpheus.service.query.BookmarkFilterQueryMap
import com.soblemprolved.orpheus.service.requests.AO3Request.Companion.BASE_HTTP_URL_BUILDER_CONFIGURATION
import com.soblemprolved.orpheus.service.requests.AO3Request.Companion.HTML_HEADERS

class BookmarksByTagRequest(
    val tag: String,
    val filterParameters: BookmarkFilterParameters,
    val page: Int,
) : GetRequest<BookmarksByTagConverter.Result> {
    override val url = BASE_HTTP_URL_BUILDER_CONFIGURATION
        .addPathSegment("tags")
        .addPathSegment(encodeTag(tag))
        .addPathSegment("bookmarks")
        .let {
            filterParameters.entries.fold(it) { acc, entry ->
                acc.addQueryParameter(entry.key, entry.value)
            }
        }
        .addQueryParameter("page", page.toString())
        .build()

    override val headers = HTML_HEADERS

    override val converter = BookmarksByTagConverter

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
