package service.requests

import model.WorkFilterParameters
import okhttp3.Headers
import okhttp3.HttpUrl
import service.converters.Converter
import service.query.WorkFilterQueryMap
import service.requests.AO3Request.Companion.HTML_HEADERS


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
    private val tag: String,
    private val filterParameters: WorkFilterParameters,
    private val page: Int,
    override val converter: Converter<T>
) : GetRequest<T> {
    init {
        require(page > 0) { "Page number cannot be zero or negative!" }
        require(tag.isNotBlank()) { "Tag cannot be blank!" }
    }

    override val url: HttpUrl = HttpUrl.Builder()
        .scheme("https")
        .host(AO3Request.AO3_HOSTNAME)
        .addPathSegment("works")
        .addPathSegment(encodeTag(tag))
        .let {
            val queryMap = WorkFilterQueryMap(filterParameters)
            queryMap.entries.fold(it) { acc, entry ->
                acc.addQueryParameter(entry.key, entry.value)
            }
        }
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
