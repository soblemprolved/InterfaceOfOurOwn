package com.soblemprolved.orpheus.service.requests

import okhttp3.Headers
import okhttp3.HttpUrl
import com.soblemprolved.orpheus.service.converters.Converter
import okhttp3.RequestBody
import java.net.URLEncoder

sealed interface AO3Request<T> {    // T is the return type
    val url: HttpUrl
    val headers: Headers
    val converter: Converter<T>

    enum class Method { // or just create classes
        GET, POST, HEAD
    }

    companion object {
        val BASE_HTTP_URL_BUILDER_CONFIGURATION: HttpUrl.Builder
            get() = HttpUrl.Builder()
                .scheme("https")
                .host("archiveofourown.org")

        val HTML_HEADERS = Headers.Builder()
            .add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
            .build()
        val JSON_HEADERS = Headers.Builder()
            .add("X-Requested-With", "XMLHttpRequest")
            .add("Accept", "application/json, text/javascript, */*; q=0.01")
            .build()


        protected fun encodeString(tag: String): String {
            return URLEncoder.encode(tag, "UTF-8")
        }
    }
}

interface GetRequest<T> : AO3Request<T> {
    fun <R> withConverter(converter: Converter<R>): GetRequest<R> {
        return object : GetRequest<R> {
            override val url = this@GetRequest.url
            override val headers = this@GetRequest.headers
            override val converter = converter
        }
    }
}

interface PostRequest<T> : AO3Request<T> {
    val requestBody: RequestBody
}
