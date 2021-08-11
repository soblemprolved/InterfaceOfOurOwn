package com.soblemprolved.orpheus.service.converters

import okhttp3.Response
import org.jsoup.Jsoup

object CsrfConverter : Converter<String> {
    override fun convert(response: Response): String {
        when (response.code) {
            200 -> return parse(response.body!!.string())
            else -> Converter.handleResponseCode(response.code)
        }

        TODO("Cannot parse this code yet")
    }

    fun parse(html: String): String {
        val doc = Jsoup.parse(html)
        return Converter.getCsrfFromJsoupDoc(doc)
    }
}
