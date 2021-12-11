package com.soblemprolved.orpheus.service.old.converters

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Response
import com.soblemprolved.orpheus.service.models.AO3Error

object AutocompleteConverter: Converter<List<String>> {
    private val format = Json { ignoreUnknownKeys = true }

    override fun convert(response: Response): List<String> {
        if (response.code !in 200..299) throw AO3Error.ServerSideError

        return parse(response.body!!.string())
    }

    fun parse(json: String): List<String> {
        val jsonEntries = format.decodeFromString<List<JsonEntry>>(json)
        return jsonEntries.map { it.name }
    }

    @Serializable
    private class JsonEntry(val name: String)
}
