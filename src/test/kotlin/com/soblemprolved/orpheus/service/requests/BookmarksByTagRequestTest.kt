package com.soblemprolved.orpheus.service.requests

import com.soblemprolved.orpheus.AO3Client
import com.soblemprolved.orpheus.extensions.AO3ClientParameterResolver
import com.soblemprolved.orpheus.model.BookmarkFilterParameters
import com.soblemprolved.orpheus.service.models.AO3Response
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(AO3ClientParameterResolver::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class BookmarksByTagRequestTest(private val client: AO3Client) {
    @Test
    fun `should not throw error external works`() {
        val request = BookmarksByTagRequest.withDefaultConverter(
            "Riza Hawkeye/Roy Mustang",
            BookmarkFilterParameters(),
            3
        )
        val response = runBlocking { client.execute(request) }
        assertTrue(response is AO3Response.Success)
    }

    @Test
    fun `should not throw error series`() {
        val request = BookmarksByTagRequest.withDefaultConverter(
            "Marvel",
            BookmarkFilterParameters(),
            1
        )
        val response = runBlocking { client.execute(request) }
        assertTrue(response is AO3Response.Success)
    }
}