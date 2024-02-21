package com.soblemprolved.interfaceofourown.service.parameterconverters

import com.soblemprolved.interfaceofourown.model.AutocompleteType
import retrofit2.Converter

internal object AutocompleteTypeConverter : Converter<AutocompleteType, String> {
    override fun convert(value: AutocompleteType): String = value.pathSegment
}
