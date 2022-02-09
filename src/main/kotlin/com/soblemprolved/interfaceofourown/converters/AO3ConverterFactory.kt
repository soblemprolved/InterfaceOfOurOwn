package com.soblemprolved.interfaceofourown.converters

import com.soblemprolved.interfaceofourown.converters.responsebody.*
import com.soblemprolved.interfaceofourown.converters.string.AutocompleteTypeConverter
import com.soblemprolved.interfaceofourown.converters.string.CsrfConverter
import com.soblemprolved.interfaceofourown.converters.string.TagUrlConverter
import com.soblemprolved.interfaceofourown.service.*
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
            Csrf::class.java ->                                 GetCsrfConverter
            Login::class.java ->                                LoginConverter
            Logout::class.java ->                               LogoutConverter
            else ->                                             super.responseBodyConverter(type, annotations, retrofit)
        }
    }

    override fun stringConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, String>? {
        return when (type) {
            AutocompleteType::class.java ->     AutocompleteTypeConverter
            Tag::class.java ->                  TagUrlConverter
            Csrf::class.java ->                 CsrfConverter
            else ->                             super.stringConverter(type, annotations, retrofit)
        }
    }
}
