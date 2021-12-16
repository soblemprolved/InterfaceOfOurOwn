package com.soblemprolved.orpheus.service

import com.soblemprolved.orpheus.service.converters.AutocompleteConverter
import com.soblemprolved.orpheus.service.models.AO3Response
import com.soblemprolved.orpheus.service.models.AutocompleteType
import com.soblemprolved.orpheus.utilities.AO3ServiceParameterResolver
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(AO3ServiceParameterResolver::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SearchAutocompleteTest(private val service: AO3Service) {
    @Test
    fun `All types should return a response`() {
        for (type in AutocompleteType.values()) {
            runBlocking {
                val response = service.searchAutocomplete(type, "full")
                print(response)
                assertTrue(response is AO3Response.Success<AutocompleteConverter.Result>)
            }
        }
    }
}
