package service.converters

import okhttp3.Response

interface Converter<T> {
    fun convert(response: Response): T
}