package com.soblemprolved.orpheus.service.converters

import okhttp3.Response
import org.jsoup.nodes.Document

interface Converter<T> {
    fun convert(response: Response): T

    companion object {
        fun getCsrfFromJsoupDoc(doc: Document): String {
            return doc.selectFirst("meta[name=csrf-token]")
                .attr("content")
        }

        fun decodeTag(encodedTag: String): String {
            return encodedTag.replace("*s*", "/")
                .replace("*a*", "&")
                .replace("*d*", ".")
                .replace("*q*", "?")
                .replace("*h*", "#")
        }
    }
}
