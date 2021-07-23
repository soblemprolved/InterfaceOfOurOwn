package com.soblemprolved.orpheus.service.requests

import com.soblemprolved.orpheus.model.WorkFilterParameters
import okhttp3.HttpUrl
import com.soblemprolved.orpheus.service.converters.Converter
import com.soblemprolved.orpheus.service.converters.WorksByTagConverter
import com.soblemprolved.orpheus.service.query.WorkFilterQueryMap
import com.soblemprolved.orpheus.service.requests.AO3Request.Companion.BASE_HTTP_URL_BUILDER_CONFIGURATION
import com.soblemprolved.orpheus.service.requests.AO3Request.Companion.HTML_HEADERS


//def self.find_by_name(string)
//    return unless string.is_a? String
//    string = string.gsub(
//      /\*[sadqh]\*/,
//      '*s*' => '/',
//      '*a*' => '&',
//      '*d*' => '.',
//      '*q*' => '?',
//      '*h*' => '#'
//)
//self.where('tags.name = ?', string).first
//end

class WorksByTagRequest<T>(
    val tag: String,
    val filterParameters: WorkFilterParameters,
    val page: Int,
    override val converter: Converter<T>
) : GetRequest<T> {
    init {
        require(page > 0) { "Page number cannot be zero or negative!" }
        require(tag.isNotBlank()) { "Tag cannot be blank!" }
    }

    override val url: HttpUrl = BASE_HTTP_URL_BUILDER_CONFIGURATION
        .addPathSegment("tags")
        .addPathSegment(encodeTag(tag))
        .addPathSegment("works")
        .let {
            val queryMap = WorkFilterQueryMap(filterParameters)
            queryMap.entries.fold(it) { acc, entry ->
                acc.addQueryParameter(entry.key, entry.value)
            }
        }
        .addQueryParameter("page", page.toString())
        .build()

    override val headers = HTML_HEADERS

    companion object {
        fun withDefaultConverter(tag: String,
                                 filterParameters: WorkFilterParameters,
                                 page: Int)
        : WorksByTagRequest<WorksByTagConverter.Result> {
            return WorksByTagRequest(tag, filterParameters, page, converter = WorksByTagConverter)
        }

        private fun encodeTag(tag: String): String {
            return tag.replace("/", "*s*")
                .replace("&", "*a*")
                .replace(".", "*d*")
                .replace("?", "*q*")
                .replace("#", "*h*")
        }
    }
}
