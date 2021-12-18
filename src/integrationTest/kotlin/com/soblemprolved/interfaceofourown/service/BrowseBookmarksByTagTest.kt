package com.soblemprolved.interfaceofourown.service

import com.soblemprolved.interfaceofourown.service.models.AO3Response
import com.soblemprolved.interfaceofourown.service.models.Tag
import com.soblemprolved.interfaceofourown.utilities.AO3ServiceParameterResolver
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(AO3ServiceParameterResolver::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class BrowseBookmarksByTagTest(private val service: AO3Service) {
    @Test
    fun `should not throw error external works`() {
        val response = runBlocking {
            service.browseBookmarksByTag(Tag("Riza Hawkeye/Roy Mustang"), 3)
        }
        assertTrue(response is AO3Response.Success)
    }

    @Test
    fun `should not throw error series`() {
        val response = runBlocking {
            service.browseBookmarksByTag(Tag("Marvel"), 1)
        }
        assertTrue(response is AO3Response.Success)
    }
}
