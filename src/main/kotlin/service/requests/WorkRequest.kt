package service.requests

import okhttp3.HttpUrl
import service.converters.Converter
import service.converters.WorkConverter
import service.requests.AO3Request.Companion.BASE_HTTP_URL_BUILDER_CONFIGURATION
import service.requests.AO3Request.Companion.HTML_HEADERS

class WorkRequest<T>(
    val id: Long,
    override val converter: Converter<T>
): GetRequest<T> {
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

    companion object {
        fun withDefaultConverter(id: Long): WorkRequest<WorkConverter.Result> {
            return WorkRequest<WorkConverter.Result>(id, converter = WorkConverter)
        }
    }
}
