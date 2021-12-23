package com.soblemprolved.interfaceofourown.service.converters.responsebody

import com.soblemprolved.interfaceofourown.service.converters.JsoupHelper
import com.soblemprolved.interfaceofourown.service.models.Csrf
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter

object GetCsrfConverter : Converter<ResponseBody, Csrf> {
    override fun convert(value: ResponseBody): Csrf {
        val html = value.string()
        val doc = Jsoup.parse(html)
        return JsoupHelper.getCsrfFromJsoupDoc(doc)
    }
}
