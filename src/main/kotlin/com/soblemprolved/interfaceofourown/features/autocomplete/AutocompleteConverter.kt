package com.soblemprolved.interfaceofourown.features.autocomplete

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody
import retrofit2.Converter

internal object AutocompleteConverter : Converter<ResponseBody, AutocompletePage> {
    private val format = Json { ignoreUnknownKeys = true }

    @Serializable
    private class JsonEntry(val name: String)

    override fun convert(value: ResponseBody): AutocompletePage {
        val json = value.string()
        val jsonEntries = format.decodeFromString<List<JsonEntry>>(json)
        return AutocompletePage(jsonEntries.map { it.name })
    }
}
