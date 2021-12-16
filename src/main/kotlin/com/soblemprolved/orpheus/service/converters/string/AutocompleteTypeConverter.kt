package com.soblemprolved.orpheus.service.converters.string

import com.soblemprolved.orpheus.service.models.AutocompleteType
import retrofit2.Converter

object AutocompleteTypeConverter : Converter<AutocompleteType, String> {
    override fun convert(value: AutocompleteType): String = value.pathSegment
}
