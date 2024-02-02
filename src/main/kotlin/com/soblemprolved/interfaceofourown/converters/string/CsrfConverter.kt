package com.soblemprolved.interfaceofourown.converters.string

import com.soblemprolved.interfaceofourown.model.Csrf
import retrofit2.Converter

/**
 * Converts [Csrf] objects into their string representations.
 * This object only applies to parameters.
 */
internal object CsrfConverter : Converter<Csrf, String> {
    override fun convert(value: Csrf): String = value.value
}
