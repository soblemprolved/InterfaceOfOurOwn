package service.requests

import AO3Client
import extensions.AO3ClientParameterResolver
import kotlinx.coroutines.runBlocking
import model.WorkFilterParameters
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import service.models.AO3Error
import service.models.AO3Response

@ExtendWith(AO3ClientParameterResolver::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class WorksByTagRequestTest(private val client: AO3Client) {
    @Test
    fun `(Integration) Successfully request works from tag`() {
        val request = WorksByTagRequest.withDefaultConverter(
            "F/M",
            WorkFilterParameters(),
            1
        )
        val response = runBlocking { client.execute(request) }
        assertTrue(response is AO3Response.Success)
        // TODO: add more stuff
    }

    @Test
    fun `(Integration) Successfully request works with non-standard rating names`() {
        val request = WorksByTagRequest.withDefaultConverter(
            "F/M",
            WorkFilterParameters(
                showRatingGeneral = false,
                showRatingTeen = false,
                showRatingMature = false,
                showRatingExplicit = false,
                showRatingNotRated = false
            ),
            1
        )
        val response = runBlocking { client.execute(request) }
        assertTrue(response is AO3Response.Success)
    }

    @Test
    fun `Return a TagSynonymError when requesting works from a synonym`() {
        val request = WorksByTagRequest.withDefaultConverter(
            "Roy Mustang/Riza Hawkeye",
            WorkFilterParameters(),
            1
        )
        val response = runBlocking { client.execute(request) }
        assertTrue(response is AO3Response.ServerError && response.error is AO3Error.TagSynonymError)
    }

    @Test
    fun `Return a TagNotFilterableError when requesting works from a tag that is not marked common`() {
        val request = WorksByTagRequest.withDefaultConverter(
            "the sea and the land as concepts of fear and desire in the face of infinity",
            WorkFilterParameters(),
            1
        )
        val response = runBlocking { client.execute(request) }
        assertTrue(response is AO3Response.ServerError && response.error is AO3Error.TagNotFilterableError)
    }

    @Test
    fun `Return an empty list when the page requested exceeds the total number of pages`() {
        val request = WorksByTagRequest.withDefaultConverter(
            "009-1 (Manga)",
            WorkFilterParameters(),
            2
        )
        val response = runBlocking { client.execute(request) }
        assertTrue(response is AO3Response.Success)
        require(response is AO3Response.Success)
        assertTrue(response.value.workBlurbs.isEmpty())
    }
}