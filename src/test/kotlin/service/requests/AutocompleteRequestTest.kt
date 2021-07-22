package service.requests

import AO3Client
import extensions.AO3ClientParameterResolver
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import service.models.AO3Response
import service.models.AutocompleteType

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
