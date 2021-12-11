package com.soblemprolved.orpheus.service.converters

import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter

object CsrfConverter : Converter<ResponseBody, String> {
    override fun convert(value: ResponseBody): String {
        val html = value.string()
        val doc = Jsoup.parse(html)
        return JsoupHelper.getCsrfFromJsoupDoc(doc)
    }
}
