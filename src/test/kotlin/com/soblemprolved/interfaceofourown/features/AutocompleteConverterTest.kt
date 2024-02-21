package com.soblemprolved.interfaceofourown.features

import com.soblemprolved.interfaceofourown.features.autocomplete.AutocompleteConverter
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AutocompleteConverterTest {
    @Test
    fun `Should return non-empty results`() {
        val json = File("src/test/resources/responses/autocomplete/with-matching-results.in")
            .readText()
        val responseBody = json.toResponseBody()
        val autocompleteResults = AutocompleteConverter.convert(responseBody)
        assertEquals(16, autocompleteResults.autocompleteResults.size)
    }

    @Test
    fun `Should return empty results`() {
        val json = File("src/test/resources/responses/autocomplete/no-matching-results.in")
            .readText()
        val responseBody = json.toResponseBody()
        val autocompleteResults = AutocompleteConverter.convert(responseBody)
        assertEquals(0, autocompleteResults.autocompleteResults.size)
    }
}
