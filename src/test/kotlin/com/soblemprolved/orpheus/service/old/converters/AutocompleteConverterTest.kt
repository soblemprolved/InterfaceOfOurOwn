package com.soblemprolved.orpheus.service.old.converters

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
        val autocompleteResults = AutocompleteConverter.parse(json)
        assertEquals(16, autocompleteResults.size)
    }

    @Test
    fun `Should return empty results`() {
        val json = File("src/test/resources/responses/autocomplete/no-matching-results.in")
            .readText()
        val autocompleteResults = AutocompleteConverter.parse(json)
        assertEquals(0, autocompleteResults.size)
    }
}
