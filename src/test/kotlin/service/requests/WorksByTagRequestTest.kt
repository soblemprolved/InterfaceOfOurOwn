package service.requests

import AO3Client
import extensions.AO3ClientParameterResolver
import kotlinx.coroutines.runBlocking
import model.WorkFilterParameters
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import service.models.AO3Response

@ExtendWith(AO3ClientParameterResolver::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class WorksByTagRequestTest(private val client: AO3Client) {
    @Test
    fun `Successfully request works from tag`() {
        val request = WorksByTagRequest.withDefaultConverter(
            "F/M",
            WorkFilterParameters(),
            1
        )
        val response = runBlocking { client.execute(request) }
        if (response is AO3Response.ServerError) System.err.println(response.error)
        assertTrue(response is AO3Response.Success)

    }

    fun `Return a TagSynonymError when requesting works from a synonym`() {

    }

    fun `Return a TagNotFilterableError when requesting works from a tag that is not marked common`() {

    }

    fun `Return an empty list when the page requested exceeds the total number of pages`() {

    }



}