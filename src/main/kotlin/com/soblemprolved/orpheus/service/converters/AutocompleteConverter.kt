package com.soblemprolved.orpheus.service.converters

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody
import retrofit2.Converter

object AutocompleteConverter : Converter<ResponseBody, AutocompleteConverter.Result> {
    data class Result(val autocompleteResults: List<String>)

    private val format = Json { ignoreUnknownKeys = true }

    @Serializable
    private class JsonEntry(val name: String)

    override fun convert(value: ResponseBody): Result {
        val json = value.string()
        val jsonEntries = format.decodeFromString<List<JsonEntry>>(json)
        return Result(jsonEntries.map { it.name })
    }
}
