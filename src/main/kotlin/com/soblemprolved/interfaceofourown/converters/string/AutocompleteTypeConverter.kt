package com.soblemprolved.interfaceofourown.converters.string

import com.soblemprolved.interfaceofourown.model.AutocompleteType
import retrofit2.Converter

object AutocompleteTypeConverter : Converter<AutocompleteType, String> {
    override fun convert(value: AutocompleteType): String = value.pathSegment
}
