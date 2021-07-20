import extensions.AO3ClientParameterResolver
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import service.models.AO3Response
import service.requests.WorkRequest

@ExtendWith(AO3ClientParameterResolver::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AO3ClientTest(private val client: AO3Client) {
    @Test
    fun `Returns a response with the suspending execute method`() {
        val workRequest = WorkRequest.from(2094438)
        val response = runBlocking { client.execute(workRequest) }
        assertFalse(response is AO3Response.NetworkError)
    }

    @Test
    fun `Returns a response with the blocking execute method`() {
        val workRequest = WorkRequest.from(2094438)
        val response = client.executeBlocking(workRequest)
        assertFalse(response is AO3Response.NetworkError)
    }
}
