package com.soblemprolved.orpheus.service.requests

import com.soblemprolved.orpheus.AO3Client
import com.soblemprolved.orpheus.model.*
import com.soblemprolved.orpheus.utilities.AO3ClientParameterResolver
import com.soblemprolved.orpheus.service.models.AO3Response
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import java.time.Month

@ExtendWith(AO3ClientParameterResolver::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class WorkRequestTest(private val client: AO3Client) {
    @Test
    fun `Successfully request non-adult oneshot work`() {
        val workRequest = WorkRequest.withDefaultConverter(2094438)
        val response = runBlocking { client.execute(workRequest) }
        assertTrue(response is AO3Response.Success && response.value.work is SingleChapterWork)
    }

    @Test
    fun `Successfully request adult oneshot work`() {
        val workRequest = WorkRequest.withDefaultConverter(2080878)
        val response = runBlocking { client.execute(workRequest) }
        assertTrue(response is AO3Response.Success && response.value.work is SingleChapterWork)
    }

    @Test
    fun `Successfully request work with anonymous author`() {
        val workRequest = WorkRequest.withDefaultConverter(32734699)
        val response = runBlocking { client.execute(workRequest) }
        assertTrue(response is AO3Response.Success && response.value.work is MultiChapterOrIncompleteWork)
        require(response is AO3Response.Success)
        with (response.value.work) {
            require(this is MultiChapterOrIncompleteWork)
            require(this.authors.size == 1)
//            require(this.giftees.size == 1)   // this shit dont work yet!
        }
    }

    @Test
    fun `Successfully request non-adult multi-chapter completed work`() {
        val workRequest = WorkRequest.withDefaultConverter(8337607)
        val response = runBlocking { client.execute(workRequest) }
        assertTrue(response is AO3Response.Success && response.value.work is MultiChapterOrIncompleteWork)
    }

    @Test
    fun `Successfully request adult multi-chapter incomplete work`() {
        val workRequest = WorkRequest.withDefaultConverter(537876)
        val response = runBlocking { client.execute(workRequest) }
        assertTrue(response is AO3Response.Success && response.value.work is MultiChapterOrIncompleteWork)
    }

    @Test
    fun `Successfully request adult single-chapter incomplete work`() {
        val workRequest = WorkRequest.withDefaultConverter(602)
        val response = runBlocking { client.execute(workRequest) }
        assertTrue(response is AO3Response.Success && response.value.work is MultiChapterOrIncompleteWork)
    }

    @Test
    fun `Should return a ServerError`() {

    }

    @Test
    fun `Should throw an unexpected error`() {

    }
}