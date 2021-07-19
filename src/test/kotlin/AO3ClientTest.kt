import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import service.models.AO3Response
import service.requests.WorkRequest

internal class AO3ClientTest {
    val okHttpClient = OkHttpClient()
    val ao3Client = AO3Client(okHttpClient)


    @Test
    fun `Returns a response with the suspending execute method`() {
        val workRequest = WorkRequest.from(2094438)
        val response = runBlocking { ao3Client.execute(workRequest) }
        assertFalse(response is AO3Response.NetworkError)
    }

    @Test
    fun `Returns a response with the blocking execute method`() {
        val workRequest = WorkRequest.from(2094438)
        val response = ao3Client.executeBlocking(workRequest)
        assertFalse(response is AO3Response.NetworkError)
    }
}
