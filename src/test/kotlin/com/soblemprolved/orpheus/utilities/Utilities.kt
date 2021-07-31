package com.soblemprolved.orpheus.utilities

import com.soblemprolved.orpheus.service.requests.GetRequest
import okhttp3.HttpUrl

internal fun <T> GetRequest<T>.toTestRequest(mockHttpUrl: HttpUrl): GetRequest<T> {
    return object: GetRequest<T> {
        override val url = this@toTestRequest.url
            .newBuilder()
            .host(mockHttpUrl.host)
            .port(mockHttpUrl.port)
            .build()

        override val headers = this@toTestRequest.headers
        override val converter = this@toTestRequest.converter
    }
}
