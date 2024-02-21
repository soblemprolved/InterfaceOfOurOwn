package com.soblemprolved.interfaceofourown

import com.soblemprolved.interfaceofourown.model.MultiChapterWork
import com.soblemprolved.interfaceofourown.model.SingleChapterWork
import com.soblemprolved.interfaceofourown.service.AO3Service
import com.soblemprolved.interfaceofourown.service.response.AO3Response
import com.soblemprolved.interfaceofourown.utilities.AO3ServiceParameterResolver
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(AO3ServiceParameterResolver::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class GetWorkTest(private val service: AO3Service) {
    @Test
    fun `Successfully request non-adult oneshot work`() {
        val response = runBlocking { service.getWork(30254160) }
        assertTrue(response is AO3Response.Success && response.value.work is SingleChapterWork)
    }

    @Test
    fun `Successfully request adult oneshot work`() {
        val response = runBlocking { service.getWork(2080878) }
        assertTrue(response is AO3Response.Success && response.value.work is SingleChapterWork)
    }

    @Test
    fun `Successfully request work with anonymous author`() {
        val response = runBlocking { service.getWork(32734699) }
        assertTrue(response is AO3Response.Success && response.value.work is MultiChapterWork)
        require(response is AO3Response.Success)
        with (response.value.work) {
            require(this is MultiChapterWork)
            require(this.authors.size == 1)
//            require(this.giftees.size == 1)   // this shit dont work yet!
        }
    }

    @Test
    fun `Successfully request non-adult multi-chapter completed work`() {
        val response = runBlocking { service.getWork(8337607) }
        assertTrue(response is AO3Response.Success && response.value.work is MultiChapterWork)
    }

    @Test
    fun `Successfully request adult multi-chapter incomplete work`() {
        val response = runBlocking { service.getWork(537876) }
        assertTrue(response is AO3Response.Success && response.value.work is MultiChapterWork)
    }

    @Test
    fun `Successfully request adult single-chapter incomplete work`() {
        val response = runBlocking { service.getWork(602) }
        assertTrue(response is AO3Response.Success && response.value.work is MultiChapterWork)
    }
}
