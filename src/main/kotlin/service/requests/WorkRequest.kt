package service.requests

import model.Work
import okhttp3.Headers
import okhttp3.HttpUrl
import service.converters.Converter
import service.converters.WorkConverter
import service.requests.AO3Request.Companion.BASE_URL
import service.requests.AO3Request.Companion.AO3_HOSTNAME
import service.requests.AO3Request.Companion.HTML_HEADERS

class WorkRequest<T>(
    private val id: Long,
    override val converter: Converter<T>
): GetRequest<T> {
    init {
        require(id >= 0)
    }

    override val url = HttpUrl.Builder()
        .scheme("https")
        .host(AO3_HOSTNAME)
        .addPathSegment("works")
        .addEncodedPathSegment(id.toString())
        .addQueryParameter("view_adult", "true")
        .addQueryParameter("view_full_work", "true")
        .build()

    override val headers = HTML_HEADERS
}
