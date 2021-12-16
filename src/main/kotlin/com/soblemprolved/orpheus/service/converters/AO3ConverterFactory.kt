package com.soblemprolved.orpheus.service.converters

import com.soblemprolved.orpheus.service.converters.string.AutocompleteTypeConverter
import com.soblemprolved.orpheus.service.models.AutocompleteType
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class AO3ConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return when (type) {
            AutocompleteConverter.Result::class.java ->         AutocompleteConverter
            BookmarksByTagConverter.Result::class.java ->       BookmarksByTagConverter
            CollectionsSearchConverter.Result::class.java ->    CollectionsSearchConverter
            WorkConverter.Result::class.java ->                 WorkConverter
            WorksByTagConverter.Result::class.java ->           WorksByTagConverter
            else ->                                             super.responseBodyConverter(type, annotations, retrofit)
        }
    }

    override fun stringConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, String>? {
        return when (type) {
            AutocompleteType::class.java -> AutocompleteTypeConverter
            else -> super.stringConverter(type, annotations, retrofit)
        }
    }
}
