package com.soblemprolved.orpheus.service

import com.soblemprolved.orpheus.service.models.AO3Response
import com.soblemprolved.orpheus.utilities.AO3ServiceParameterResolver
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(AO3ServiceParameterResolver::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class BrowseCollectionsTest(private val service: AO3Service) {
    @Test
    fun `should work`() {
        val response = runBlocking { service.browseCollections(1) }
        assertTrue(response is AO3Response.Success && response.value.collectionCount > 20)
    }
}
