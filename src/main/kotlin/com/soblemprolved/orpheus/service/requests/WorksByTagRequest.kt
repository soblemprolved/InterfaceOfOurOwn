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

class WorksByTagRequest(
    val tag: String,
    val filterParameters: WorkFilterParameters,
    val page: Int,
) : GetRequest<WorksByTagConverter.Result> {
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

    override val converter = WorksByTagConverter

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
