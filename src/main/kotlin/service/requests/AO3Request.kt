package service.requests

import okhttp3.Headers
import okhttp3.HttpUrl
import service.converters.Converter
import java.net.URLEncoder

sealed interface AO3Request<T> {    // T is the return type
    val url: HttpUrl
    val headers: Headers
    val converter: Converter<T>

    enum class Method { // or just create classes
        GET, POST, HEAD
    }

    companion object {
        const val BASE_URL = "https://archiveofourown.org/"

        const val AO3_HOSTNAME = "archiveofourown.org"
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

interface GetRequest<T> : AO3Request<T>

//interface PostRequest<T> : AO3Request<T> {
//    val requestBody: String
//}
