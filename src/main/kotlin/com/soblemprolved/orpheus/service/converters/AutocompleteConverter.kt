package com.soblemprolved.orpheus.service.converters

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody
import retrofit2.Converter

object AutocompleteConverter : Converter<ResponseBody, List<String>> {
    private val format = Json { ignoreUnknownKeys = true }

    override fun convert(value: ResponseBody): List<String> {
        val json = value.string()
        val jsonEntries = format.decodeFromString<List<JsonEntry>>(json)
        return jsonEntries.map { it.name }
    }

    @Serializable
    private class JsonEntry(val name: String)
}