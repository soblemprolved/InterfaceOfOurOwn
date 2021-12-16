package com.soblemprolved.interfaceofourown.service.converters.string

import com.soblemprolved.interfaceofourown.service.models.AutocompleteType
import retrofit2.Converter

object AutocompleteTypeConverter : Converter<AutocompleteType, String> {
    override fun convert(value: AutocompleteType): String = value.pathSegment
}
