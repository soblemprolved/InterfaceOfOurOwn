package service.requests

import okhttp3.HttpUrl
import service.converters.AutocompleteConverter
import service.converters.Converter
import service.models.AutocompleteType
import service.requests.AO3Request.Companion.BASE_HTTP_URL_BUILDER_CONFIGURATION
import service.requests.AO3Request.Companion.JSON_HEADERS

class AutocompleteRequest<T>(
    val type: AutocompleteType,
    val query: String,
    override val converter: Converter<T>
) : GetRequest<T> {
    override val url: HttpUrl = BASE_HTTP_URL_BUILDER_CONFIGURATION
        .addPathSegment("autocomplete")
        .addPathSegment(type.pathSegment)
        .addQueryParameter("term", query)
        .build()

    override val headers = JSON_HEADERS

    companion object {
        fun withDefaultConverter(type: AutocompleteType, query: String): AutocompleteRequest<List<String>> {
            return AutocompleteRequest<List<String>>(type, query, converter = AutocompleteConverter)
        }
    }
}
