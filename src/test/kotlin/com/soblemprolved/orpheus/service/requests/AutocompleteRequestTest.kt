package com.soblemprolved.orpheus.service.requests

import com.soblemprolved.orpheus.AO3Client
import com.soblemprolved.orpheus.utilities.AO3ClientParameterResolver
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import com.soblemprolved.orpheus.service.models.AO3Response
import com.soblemprolved.orpheus.service.models.AutocompleteType

@ExtendWith(AO3ClientParameterResolver::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AutocompleteRequestTest(private val client: AO3Client) {
    @Test
    fun `All types should return a response`() {
        for (type in AutocompleteType.values()) {
            val request = AutocompleteRequest.withDefaultConverter(type, "full")
            val response = runBlocking{ client.execute(request) }
            assertTrue(response is AO3Response.Success)
        }
    }

    @Test
    fun `Should return non-empty results`() {
        val request = AutocompleteRequest.withDefaultConverter(AutocompleteType.TAG, "full")
        val response = runBlocking{ client.execute(request) }
        assertTrue(response is AO3Response.Success && response.value.isNotEmpty())
    }

    @Test
    fun `Should return empty results`() {
        val request = AutocompleteRequest.withDefaultConverter(AutocompleteType.TAG, "fyjtfhfv")
        val response = runBlocking{ client.execute(request) }
        assertTrue(response is AO3Response.Success && response.value.isEmpty())
    }
}
