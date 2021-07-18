package service.converters

import okhttp3.Response
import org.jsoup.nodes.Document
import java.net.URLEncoder

interface Converter<T> {
    data class Result<R>(val value: R, val csrfToken: String)   // FIXME: why does <out R> work here?
    fun convert(response: Response): Result<T>

    companion object {
        protected fun getCsrfFromJsoupDoc(doc: Document): String {
            return doc.selectFirst("meta[name=csrf-token]")
                .attr("content")
        }
    }
}
