package com.soblemprolved.interfaceofourown

import com.soblemprolved.interfaceofourown.service.response.AO3Response
import com.soblemprolved.interfaceofourown.model.AutocompleteType
import com.soblemprolved.interfaceofourown.features.autocomplete.AutocompletePage
import com.soblemprolved.interfaceofourown.service.AO3Service
import com.soblemprolved.interfaceofourown.utilities.AO3ServiceParameterResolver
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
                assertTrue(response is AO3Response.Success<AutocompletePage>)
            }
        }
    }
}
