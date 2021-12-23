package com.soblemprolved.interfaceofourown.service

import com.soblemprolved.interfaceofourown.service.converters.responsebody.AutocompleteConverter
import com.soblemprolved.interfaceofourown.service.models.AO3Response
import com.soblemprolved.interfaceofourown.service.models.AutocompleteType
import com.soblemprolved.interfaceofourown.service.models.Csrf
import com.soblemprolved.interfaceofourown.utilities.AO3ServiceParameterResolver
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(AO3ServiceParameterResolver::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class GetCsrfTokenTest(private val service: AO3Service) {
    @Test
    fun `Should retrieve a csrf token`() {
        for (type in AutocompleteType.values()) {
            runBlocking {
                val response = service.getCsrfToken()
                assertTrue(response is AO3Response.Success<Csrf>)
            }
        }
    }
}
