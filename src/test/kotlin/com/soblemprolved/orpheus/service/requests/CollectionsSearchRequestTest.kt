package com.soblemprolved.orpheus.service.requests

import com.soblemprolved.orpheus.AO3Client
import com.soblemprolved.orpheus.utilities.AO3ClientParameterResolver
import com.soblemprolved.orpheus.model.CollectionFilterParameters
import com.soblemprolved.orpheus.service.models.AO3Response
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(AO3ClientParameterResolver::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CollectionsSearchRequestTest(private val client: AO3Client) {
    @Test
    fun `should work`() {
        val request = CollectionsSearchRequest.withDefaultConverter(
            CollectionFilterParameters(),
            1
        )
        val response = runBlocking { client.execute(request) }
        assertTrue(response is AO3Response.Success && response.value.collectionCount > 20)
    }
}
