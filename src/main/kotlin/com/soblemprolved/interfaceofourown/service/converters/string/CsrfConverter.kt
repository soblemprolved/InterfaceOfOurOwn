package com.soblemprolved.interfaceofourown.service.converters.string

import com.soblemprolved.interfaceofourown.service.models.Csrf
import retrofit2.Converter

/**
 * Converts `Csrf` objects into their string representations.
 * This object only applies to parameters.
 */
object CsrfConverter : Converter<Csrf, String> {
    override fun convert(value: Csrf): String = value.value
}
