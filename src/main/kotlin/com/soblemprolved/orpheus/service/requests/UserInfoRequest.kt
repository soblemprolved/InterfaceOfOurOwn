package com.soblemprolved.orpheus.service.requests

import com.soblemprolved.orpheus.model.UserName
import com.soblemprolved.orpheus.service.converters.Converter
import com.soblemprolved.orpheus.service.requests.AO3Request.Companion.BASE_HTTP_URL_BUILDER_CONFIGURATION
import com.soblemprolved.orpheus.service.requests.AO3Request.Companion.HTML_HEADERS

class UserInfoRequest<T>(
    val username: String,
    val pseudname: String?,
    override val converter: Converter<T>
) : GetRequest<T> {
    constructor(userName: UserName, converter: Converter<T>) : this(userName.username, userName.pseudonym, converter)

    override val url = BASE_HTTP_URL_BUILDER_CONFIGURATION
        .addPathSegment("users")
        .addPathSegment(username)
        .let {
            if (pseudname == null) {
                return@let it
            } else {
                return@let it.addPathSegment("pseuds")
                    .addPathSegment(pseudname)
            }
        }
        .build()

    override val headers = HTML_HEADERS
}
